package com.imagsky.v81_android;

import com.google.gson.Gson;
import com.imagsky.v81_android.constants.SysProperties;
import com.imagsky.v81_android.core.AppContext;
import com.imagsky.v81_android.core.AsyncTaskPostbackInterface;
import com.imagsky.v81_android.core.JsonResponse;
import com.imagsky.v81_android.domain.App;
import com.imagsky.v81_android.util.SharedPreferences;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.Toast;

public class FullscreenActivity extends Activity implements AsyncTaskPostbackInterface {
    private Animation myFadeInAnimation;

    protected AppContext appState;
    private int thisActivityStage = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appState = ((AppContext) getApplicationContext());

        setContentView(R.layout.activity_fullscreen);

        //final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.logoscreen_content);

        ImageView bbmLogoView= (ImageView)findViewById(R.id.iv_bbm_logo);
        ImageView customLogoView= (ImageView)findViewById(R.id.iv_custom_logo); //TODO: Change to Shop Custom Image in xml

        myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.logofadein);
        bbmLogoView.startAnimation(myFadeInAnimation);

        customLogoView.setVisibility(View.INVISIBLE);

        thisActivityStage = 1; //Show BBM Logo

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("FullscreenActivity","onclick");
                if(thisActivityStage==1) {
                    Toast.makeText(FullscreenActivity.this.getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                    ImageView bbmLogoView = (ImageView) findViewById(R.id.iv_bbm_logo);
                    bbmLogoView.setVisibility(View.INVISIBLE);
                    ImageView customLogoView = (ImageView) findViewById(R.id.iv_custom_logo); //TODO: Change to Shop Custom Image in xml
                    customLogoView.setVisibility(View.VISIBLE);
                    customLogoView.startAnimation(myFadeInAnimation);
                    thisActivityStage++;
                } else if(thisActivityStage==2){
                    //Click after display custom logo
                    if(SysProperties._ACCESS_INTERNET && !SysProperties._TRIAL_VERSION){
                        //Send Request to check update
                        SharedPreferences.getApplicationFromInternet(FullscreenActivity.this); //Result would be caught in onTaskComplete handler
                    } else {
                        //Use local Content
                        //App thisApp = SharedPreferences.getApplication(FullscreenActivity.this);
                        App thisApp = SharedPreferences.getApplicationFromFile(FullscreenActivity.this);
                        if(thisApp==null){
                            Toast.makeText(FullscreenActivity.this.getApplicationContext(), "Loading file error", Toast.LENGTH_LONG).show();
                        } else {
                            appState.setMyApp(thisApp);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

    }

    /***
     * Override method of implementing interface AsyncTaskPostbackInterface
     * @param result
     */
    @Override
    public void onTaskComplete(String result){
        Gson gson = new Gson();
        JsonResponse json = null;
        try {
            Log.d("Internet", result);
            json = gson.fromJson(result, JsonResponse.class);
             if(json.getModule().equalsIgnoreCase("UPDATE")){
                App thisApp = gson.fromJson(new String(Base64.decode(json.getAppobjjson_64(), Base64.DEFAULT),"UTF-8"), App.class);
                AppContext appState = ((AppContext)this.getApplicationContext());
                appState.setMyApp(thisApp);
                //Save the updated app locally
                SharedPreferences.saveApplication(this, thisApp);
            }
        } catch (Exception e){
            Log.e("Save Error", result, e);
        }
    }


}
