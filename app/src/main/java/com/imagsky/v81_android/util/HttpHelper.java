package com.imagsky.v81_android.util;

/**
 * HttpHelper : Basic Internet connection setup and receive response
 * Source from WLPlay version 1
 * Created by jasonmak on 29/1/2015.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;
import com.imagsky.v81_android.core.AsyncTaskPostbackInterface;
import com.imagsky.v81_android.core.JsonResponse;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class HttpHelper  extends AsyncTask<Object, Void, String> {

    private AsyncTaskPostbackInterface listener;
    private Context thisContext;

    public HttpHelper(AsyncTaskPostbackInterface listener){
        this.listener = listener;
        this.thisContext = (Context)listener;
    }

    @Override
    protected String doInBackground(Object... arg0) {
        ConnectivityManager connMgr = (ConnectivityManager)
                thisContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try{
                return downloadUrl((String)arg0[0], (HashMap<String, String>) arg0[1]);
            } catch (IOException e) {
                String[] tokens = CommonUtil.stringTokenize((String)arg0[0], "/");
                JsonResponse result = new JsonResponse();
                result.setStatus("500");
                result.setModule(tokens[tokens.length-1]);
                result.setMessage("Unable to retrieve web page. URL may be invalid.");
                Gson aGson = new Gson();
                return aGson.toJson(result);
            }
        } else {
            String[] tokens = CommonUtil.stringTokenize((String)arg0[0], "/");
            JsonResponse result = new JsonResponse();
            result.setStatus("500");
            result.setModule(tokens[tokens.length-1]);
            result.setMessage("No network connection available.");
            Gson aGson = new Gson();
            return aGson.toJson(result);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onTaskComplete(result);
    }

    private String downloadUrl(String myurl,	HashMap<String, String> paramMap) throws IOException {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            if (Thread.interrupted()) {
                throw new InterruptedException("Thread interrupted");
            }
            // 建立連線
            /**
             * URL targetUrl = new URL(
             * "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&langpair=en%7Czh-TW&q="
             * + q);
             ***/
            URL targetUrl = new URL(myurl);
            conn = (HttpURLConnection) targetUrl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            conn.setRequestProperty("Accept","*/*");
            conn.setUseCaches(false);

            StringBuffer sb = new StringBuffer();
            for (String key : paramMap.keySet()) {
                if (!CommonUtil.isNullOrEmpty(sb.toString())) {
                    sb.append("&");
                }
                Log.d("Post","key:"+ key+";value:"+ paramMap.get(key));
                sb.append(key + "="
                        + URLEncoder.encode(CommonUtil.null2Empty(paramMap.get(key)), "UTF-8"));
            }

            conn.setRequestProperty("Content-Length",
                    "" + Integer.toString(sb.toString().getBytes().length));
            conn.setFixedLengthStreamingMode(sb.toString().getBytes().length);

            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            DataOutputStream out = null;

            out = new DataOutputStream(conn.getOutputStream());
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();


            int response = conn.getResponseCode();
            Log.d("Internet", "The response is: " + response);
            is = conn.getInputStream();

            StringBuffer output = new StringBuffer("");
            try {
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(is));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                String[] tokens = CommonUtil.stringTokenize(myurl, "/");
                JsonResponse result = new JsonResponse();
                result.setStatus("500");
                result.setModule(tokens[tokens.length-1]);
                result.setMessage("BufferReader exception "+ e1.getMessage());
                Gson aGson = new Gson();
                Log.e("Internet", "",e1);
                return aGson.toJson(result);
            }
            return output.toString();
        } catch (Exception e) {
            String[] tokens = CommonUtil.stringTokenize(myurl, "/");
            JsonResponse result = new JsonResponse();
            result.setStatus("500");
            result.setModule(tokens[tokens.length-1]);
            result.setMessage("Internet exception "+ e.getMessage());
            Log.e("Internet", "",e);
            Gson aGson = new Gson();
            return aGson.toJson(result);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }
}

