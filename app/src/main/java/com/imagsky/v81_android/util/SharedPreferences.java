package com.imagsky.v81_android.util;

import android.app.Activity;
import android.content.*;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imagsky.v81_android.core.AppContext;
import com.imagsky.v81_android.core.AsyncTaskPostbackInterface;
import com.imagsky.v81_android.domain.App;
import com.imagsky.v81_android.domain.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jasonmak on 29/1/2015.
 */
public class SharedPreferences {

    private static final String sharedPreferenceName = "v81";

    private static final String thisAppContent = "v81app";

    private static final String presetAppFilename = "appfile.txt";

    /***
     * Get application from local SharedPreferences
     * @param activity
     * @return app
     */
    public static App getApplication(Activity activity){
        android.content.SharedPreferences sp = activity.getSharedPreferences(sharedPreferenceName, 0);
        String playerJsonStr = sp.getString("thisAppContent", "");
        Gson gson = new Gson();
        App thisApp = gson.fromJson(playerJsonStr, App.class);
        return thisApp;
    }

    public static App getApplicationFromFile(Activity activity){
        AssetManager am = activity.getAssets();
        InputStream is;
        try {
            is = am.open(presetAppFilename);

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (br != null) {
                        br.close();
                }
            }
            Log.d("GSON","json string:" + sb.toString());
            Gson gson = null;
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Module.class   , new IModuleAdapterForGson());
            gson = builder.create();
            App thisApp = gson.fromJson(sb.toString(), App.class);
            return thisApp;
        } catch (IOException e){
            return null;
        }
    }
    /***
     * Request a update from server
     * Server response would be caught in the onTaskComplete method of the  activity (implements AsyncTaskPostbackInterface)
     * @param activity
     */
    public static void getApplicationFromInternet(Activity activity){
        AppContext appState = ((AppContext)activity.getApplicationContext());
        InternetConnectionService.requestUpdate((AsyncTaskPostbackInterface)activity, appState.getMachineId(), appState.getShopId(), appState.getAppMode(),  new java.util.Date().getTime()); //Null Password
        //Server response would be caught in the onTaskComplete method of the  activity (implements AsyncTaskPostbackInterface)
    }

    public static void saveApplication(Activity activity, App thisApp){
        android.content.SharedPreferences settings = activity.getSharedPreferences(sharedPreferenceName, 0);
        android.content.SharedPreferences.Editor PE = settings.edit();

        Gson gson = new GsonBuilder()
        //        .setExclusionStrategies(new GsonExclusionForPlayer())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                .create();
        String jsonStrPy = gson.toJson(thisApp, App.class);
        PE.putString(thisAppContent, jsonStrPy);
        PE.commit();
    }
}

