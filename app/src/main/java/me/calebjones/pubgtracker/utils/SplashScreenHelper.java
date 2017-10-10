package me.calebjones.pubgtracker.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import me.calebjones.pubgtracker.R;

public class SplashScreenHelper implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // apply the actual theme
        activity.setTheme(R.style.AppTheme);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    // ... other callbacks are empty
}
