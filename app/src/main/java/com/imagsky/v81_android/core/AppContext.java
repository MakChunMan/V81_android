package com.imagsky.v81_android.core;

/**
 * Created by jasonmak on 29/1/2015.
 */
import com.imagsky.v81_android.domain.App;
import com.imagsky.v81_android.util.SharedPreferences;
import com.imagsky. v81_android.util.InternetConnectionService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
//import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import android.widget.ImageView;
import android.widget.Toast;

public class AppContext extends Application {

    private String machineId;

    private String shopId;

    private String appMode; //"S" = Single, "M" = Mall

    //private static final int _DESIGN_WIDTH = 600;
    //private static final int _DESIGN_HEIGHT = 800;

    //Internet Connection Tokens / information
    private String internetToken;
    private long internetTimestamp;

    private App myApp;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public App getMyApp() {
        return myApp;
    }

    public void setMyApp(App myApp) {
        this.myApp = myApp;
    }

    /****
    private ServerMessage serverMessage;

    public ServerMessage getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;
    }***/



    public long getInternetTimestamp() {
        return internetTimestamp;
    }

    public void setInternetTimestamp(long internetTimestamp) {
        this.internetTimestamp = internetTimestamp;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAppMode() {
        return appMode;
    }

    public void setAppMode(String appMode) {
        this.appMode = appMode;
    }

    /************** Save *************/
    public void saveLocal(Activity myContext){
        SharedPreferences.saveApplication(myContext, myApp);
    }

    /****
    public void save(Activity myContext){
        SharedPreferences.saveApplication(myContext, myApp);

        Gson gson = new GsonBuilder()
                //.setExclusionStrategies(new GsonExclusionForPlayer())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                .create();
        String jsonStrApp = gson.toJson(myApp, App.class);
        //Log.d("SAVELOAD","using exclusion - "+jsonStrPy);


        //
        // Save to internet to overwrite the dummy
        JsonResponse json = new JsonResponse();
        //TODO: json parameters
        /**
        json.setEmail(aPlayer.getEmail());
        json.setPassword(aPlayer.getPasswd());
        json.setToken(this.getInternetToken());
        json.setTimestamp(this.getInternetTimestamp());
        try {
            json.setAppobjjson_64(Base64.encodeToString(jsonStrApp.getBytes("UTF-8"), Base64.DEFAULT));
            InternetConnectionService.requestUpdate((AsyncTaskPostbackInterface)myContext, json, null); //Null Password
        } catch (Exception e) {
            Toast.makeText(myContext, "網絡未能連線, 儲存失敗", Toast.LENGTH_LONG).show();
            Log.e("Save", "Try-catch from Base64 from player object", e);
        }
    }***/
}

