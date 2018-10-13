package com.example.csimcik.bakingapp;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.IdlingResource;

public class IdleRES implements IdlingResource {
    private final Context context;
    private ResourceCallback resourceCallback;

    public IdleRES(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return IdleRES.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = !isDataLoaded();
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }


    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
            this.resourceCallback = resourceCallback;
    }
    private boolean isDataLoaded() {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo info : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (RecipeGetter.class.getName().equals(info.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
