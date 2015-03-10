package com.imagsky.v81_android.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.imagsky.v81_android.domain.Module;

/**
 * Created by jasonmak on 10/3/2015.
 */
public class MainMenuButtonOnClickListener implements View.OnClickListener {

    private static final String ACTIVITY_CLASS_PREFIX = "com.imagsky.v81_android.activities.";

    public MainMenuButtonOnClickListener(Module module){
        thisModule = module;
        try {
            this.targetActivity = Class.forName(ACTIVITY_CLASS_PREFIX + module.getModuleType()+"Activity").asSubclass(Activity.class);
        } catch (ClassNotFoundException e){
            Log.e("", ACTIVITY_CLASS_PREFIX + module.getModuleType(), e);
        }
    }
    private Module thisModule;
    public Class<? extends Activity> targetActivity;

    @Override
    public void onClick(View v) {
        Log.d("OnClickListener", this.targetActivity.getName());
        Intent intent = new Intent(v.getContext(), targetActivity);
        v.getContext().startActivity(intent);
    }

}
