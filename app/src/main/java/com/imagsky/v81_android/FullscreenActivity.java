package com.imagsky.v81_android;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.imagsky.v81_android.constants.SysProperties;
import com.imagsky.v81_android.core.AppContext;
import com.imagsky.v81_android.core.AsyncTaskPostbackInterface;
import com.imagsky.v81_android.core.JsonResponse;
import com.imagsky.v81_android.domain.App;
import com.imagsky.v81_android.domain.Module;
import com.imagsky.v81_android.util.CommonUtil;
import com.imagsky.v81_android.util.MainMenuButtonOnClickListener;
import com.imagsky.v81_android.util.SharedPreferences;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FullscreenActivity extends Activity implements AsyncTaskPostbackInterface {
    private Animation myFadeInAnimation;

    protected AppContext appState;
    private int thisActivityStage = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appState = ((AppContext) getApplicationContext());

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Logo Frame
        setContentView(R.layout.activity_fullscreen);


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
                            setContentView(getMainMenuTemplateLayout(thisApp)); //Assign MainMenu Layout

                            LinearLayout mainmenu2colScreen = (LinearLayout)findViewById(R.id.mainmenu_2col_screen);
                            TextView mainmenu_app_header = (TextView)findViewById(R.id.mainmenu_app_header);
                            mainmenu_app_header.setTextSize(20);
                            mainmenu_app_header.setGravity(Gravity.CENTER);
                            mainmenu_app_header.setText(thisApp.getAPP_NAME());

                            if(thisApp.getAPP_MAINMENU_BG()==null){
                                mainmenu2colScreen.setBackgroundColor(0xFF6291CF);
                                Log.e("MainMenu","APP MAINMENU_BG is null");
                            } else if(CommonUtil.isNullOrEmpty(thisApp.getAPP_MAINMENU_BG().getImageUrl())){
                                mainmenu2colScreen.setBackgroundColor(0xFF6291CF);
                                Log.e("MainMenu","APP MAINMENU_BG is empty");
                            }
                            if(thisApp.getAPP_MAINMENU_BG()!=null && !CommonUtil.isNullOrEmpty(thisApp.getAPP_MAINMENU_BG().getImageUrl())) {
                                try {

                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        mainmenu2colScreen.setBackground(Drawable.createFromStream(FullscreenActivity.this.getAssets().open(thisApp.getAPP_MAINMENU_BG().getImageUrl()), null));
                                    } else {
                                        mainmenu2colScreen.setBackgroundDrawable(Drawable.createFromStream(FullscreenActivity.this.getAssets().open(thisApp.getAPP_MAINMENU_BG().getImageUrl()), null));
                                    }
                                } catch (IOException ioe) {
                                    Log.e("MainMenu", "Load bg file error:"+thisApp.getAPP_MAINMENU_BG().getImageUrl());
                                }
                            }

                            //The following code is used to measure the dimensions of a view/ layout, since the dimension cannot be obtain
                            //in the onCreate stage, a listener is needed once the layout is drawn
                            final LinearLayout buttonColumn =  (LinearLayout) findViewById(R.id.mainmenu_2col_left);
                            buttonColumn.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                            final ViewTreeObserver observer= buttonColumn.getViewTreeObserver();
                            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    Log.d("","Listener Width:"+ buttonColumn.getWidth() + "; height:"+ buttonColumn.getHeight());
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        observer.removeOnGlobalLayoutListener(this);
                                    } else {
                                        observer.removeGlobalOnLayoutListener(this);
                                    }
                                    rendorMainmenuButton(buttonColumn.getWidth() );
                                }
                            });
                            //End of getting dimension of the layout
                        }
                    }
                }
            }
        });
    }

    /***
     *
     */
    protected int rendorMainmenuButton(int buttonWidth){
        App thisApp = appState.getMyApp();
        if(thisApp==null || thisApp.getModules()==null || thisApp.getModules().size()==0)
            return -1;
        ImageButton thisButton;
        List<Module> thisList = new ArrayList<Module>(thisApp.getModules());
        LinearLayout leftLayout = (LinearLayout) findViewById(R.id.mainmenu_2col_left);
        LinearLayout rightLayout = (LinearLayout) findViewById(R.id.mainmenu_2col_right);
        LinearLayout workingLayout;
        Collections.sort(thisList);

        Drawable icon = null;
        TextView tv = null;
        for(int x = 0; x < thisList.size(); x++){
            workingLayout = (x % 2 ==0)?leftLayout:rightLayout;
            thisButton = new ImageButton(this);
            thisButton.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, buttonWidth));
            thisButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            try {
                if (thisList.get(x).getModIcon() != null && !CommonUtil.isNullOrEmpty(thisList.get(x).getModIcon().getImageUrl())) {
                    icon = Drawable.createFromStream(FullscreenActivity.this.getAssets().open(thisList.get(x).getModIcon().getImageUrl()), null);
                } else {
                    icon = getResources().getDrawable(thisList.get(x).getDefaultModuleIconId());
                }
            } catch (IOException ioe) {
                    icon = getResources().getDrawable(thisList.get(x).getDefaultModuleIconId());
                    Log.e("MainMenu", "Load button bg file error:"+thisList.get(x).getModIcon().getImageUrl());
            }
            thisButton.setBackgroundColor(0x44FFFFFF); //Set button icon
            thisButton.setImageDrawable(icon);
            thisButton.setOnClickListener(new MainMenuButtonOnClickListener((Module) thisList.get(x)));
            workingLayout.addView(thisButton);

            //Text below the icon
            tv = new TextView(this);
            tv.setText(thisList.get(x).getModuleTitle());
            tv.setTextColor(0xFFFFFFFF);
            tv.setLayoutParams(new ViewGroup.LayoutParams(buttonWidth, buttonWidth / 2));
            tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            //tv.setTextSize(20);
            workingLayout.addView(tv);
        }
        return 0;
    }

    /*****
     * Return R.layout id for specific template of main menu
     * @param app
     * @return
     */
    protected int getMainMenuTemplateLayout(App app){
        if(app==null)
            return -1;
        else {
            try {
                //TODO remove hardcode
                //Field field = R.layout.class.getDeclaredField("main_menu_" + app.getAPP_TEMPLATE().toLowerCase() + "_portrait");
                Field field = R.layout.class.getDeclaredField("main_menu_2col_portrait");
                field.setAccessible(true);

                int resultIdx = field.getInt(null);
                return resultIdx;
            } catch (NoSuchFieldException e){
                Log.d("FullscreenActivity", "NoSuchField - " + "main_menu_" + app.getAPP_TEMPLATE().toLowerCase() + "_portrait");
                return -1;
            } catch (IllegalAccessException e){
                Log.d("FullscreenActivity", "IllegalAccessException - " + "main_menu_" + app.getAPP_TEMPLATE().toLowerCase() + "_portrait");
                return -1;
            }
        }
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
