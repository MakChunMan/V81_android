package com.imagsky.v81_android.util;

/**
 * InternetConnectionService: Specific connection for V81 request for server
 * Created by jasonmak on 29/1/2015.
 */
import java.util.HashMap;

import android.util.Log;
import com.imagsky.v81_android.core.AsyncTaskPostbackInterface;


public class InternetConnectionService {

    /***********INTERNET DEMO CODE *********
     //Test Internet
     Log.d("Internet","Start");
     HttpHelper aHelper = new HttpHelper();
     aHelper.start(this, "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=en", new HashMap<String,String>());
     *********************************/

    private final static String APP_MODE = "mode";
    public final static String MODE_SINGLE = "s";
    public final static String MODE_MALL = "m";

    //private final static String EMAIL = "email";
    private final static String TIMESTAMP = "ts";
    private final static String MACHINE_ID = "machine_id";
    private final static String SHOP_ID = "shop_id";
    //private final static String PASSWD = "pwd";
    //private final static String TOKEN = "token";
    private final static String JSON = "json";
    private final static String DEFAULT_HOST = "http://www.buybuymeat.net/wlplay/json/"; //TODO: Need change

    public static void requestUpdate(AsyncTaskPostbackInterface listener, String machineId, String shopId, String mode, long timestamp){
        Log.d("Internet","Start Request Update");
        HttpHelper aHelper = new HttpHelper(listener);
        HashMap<String, String> aMap = new HashMap<String, String>();
        aMap.put(APP_MODE, mode);
        if(MODE_SINGLE.equalsIgnoreCase(CommonUtil.null2Empty(mode)))
            aMap.put(SHOP_ID, shopId);
        aMap.put(MACHINE_ID, machineId);
        aMap.put(TIMESTAMP, ""+timestamp);
        aHelper.execute(DEFAULT_HOST + "update", aMap);
        Log.d("Internet","End Request Update");
    }

    /***
    public static void login(AsyncTaskPostbackInterface registerActivity, JsonResponse json, String password) {
        Log.d("Internet","Start Login");
        HttpHelper aHelper = new HttpHelper(registerActivity);
        HashMap<String, String> aMap = new HashMap<String, String>();
        aMap.put(EMAIL, json.getEmail());
        aMap.put(TIMESTAMP, ""+json.getTimestamp().getTime());
        aMap.put(PASSWD, password);
        aMap.put(TOKEN, json.getToken());
        aHelper.execute(DEFAULT_HOST + "login", aMap);
        Log.d("Internet","End Request Login");
    }

    public static void register(AsyncTaskPostbackInterface registerActivity, JsonResponse json, String password) {
        Log.d("Internet","Start Register");
        HttpHelper aHelper = new HttpHelper(registerActivity);
        HashMap<String, String> aMap = new HashMap<String, String>();
        aMap.put(EMAIL, json.getEmail());
        aMap.put(TIMESTAMP, ""+json.getTimestamp().getTime());
        aMap.put(PASSWD, password);
        aMap.put(TOKEN, json.getToken());
        aMap.put(JSON, json.getAppobjjson_64());
        aHelper.execute(DEFAULT_HOST + "reg", aMap);
        Log.d("Internet","End Request Register");
    }

    public static void save(AsyncTaskPostbackInterface registerActivity, JsonResponse json, String password){
        Log.d("Internet","Start Save");
        HttpHelper aHelper = new HttpHelper(registerActivity);
        HashMap<String, String> aMap = new HashMap<String, String>();
        aMap.put(EMAIL, json.getEmail());
        aMap.put(TIMESTAMP, ""+json.getTimestamp().getTime());
        aMap.put(PASSWD, password);
        aMap.put(TOKEN, json.getToken());
        aMap.put(JSON, json.getAppobjjson_64());
        aHelper.execute(DEFAULT_HOST + "save", aMap);
        Log.d("Internet","End Request Save");
    }

    public static void getNews(AsyncTaskPostbackInterface registerActivity, JsonResponse json, String password){
        Log.d("Internet","Get News");
        HttpHelper aHelper = new HttpHelper(registerActivity);
        HashMap<String, String> aMap = new HashMap<String, String>();
        aMap.put(EMAIL, json.getEmail());
        aMap.put(TIMESTAMP, ""+json.getTimestamp().getTime());
        aMap.put(PASSWD, password);
        aMap.put(TOKEN, json.getToken());
        aHelper.execute(DEFAULT_HOST + "news", aMap);
        Log.d("Internet","End Get News");
    }***/
}

