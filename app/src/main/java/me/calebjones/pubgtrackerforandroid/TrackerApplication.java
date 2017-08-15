package me.calebjones.pubgtrackerforandroid;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import io.realm.Realm;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import timber.log.Timber;

public class TrackerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        DataClient.create();
        Timber.plant(new Timber.DebugTree());
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
    }
}
