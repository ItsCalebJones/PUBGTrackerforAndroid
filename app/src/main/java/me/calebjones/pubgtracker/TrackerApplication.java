package me.calebjones.pubgtracker;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatDelegate;
import android.text.format.DateFormat;
import android.widget.ImageView;

import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.ads.MobileAds;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtracker.data.DataManager;
import me.calebjones.pubgtracker.data.networking.DataClient;
import me.calebjones.pubgtracker.utils.GlideApp;
import me.calebjones.pubgtracker.utils.SplashScreenHelper;
import timber.log.Timber;
import com.crashlytics.android.Crashlytics;

import java.util.Locale;
import java.util.TimeZone;

import io.fabric.sdk.android.Fabric;

public class TrackerApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerActivityLifecycleCallbacks(new SplashScreenHelper());
        Realm.init(this);

        MobileAds.initialize(this, "ca-app-pub-9824528399164059~7893837161");
                /*
        * Init Crashlytics and gather additional device information.
        * Always leave this at the top so it catches any init failures.
        * Version 1.3.0-Beta had a bug where starting a service crashed before Crashlytics picked it up.
        */
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder()
                        .disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);

        // Initialize Fabric with the debug-disabled crashlytics.
        Crashlytics.setString("Timezone", String.valueOf(TimeZone.getDefault().getDisplayName()));
        Crashlytics.setString("Language", Locale.getDefault().getDisplayLanguage());
        Crashlytics.setBool("is24", DateFormat.is24HourFormat(getApplicationContext()));

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        DataClient.create();
        DataManager.create();
        Timber.plant(new Timber.DebugTree());

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                GlideApp.with(imageView.getContext())
                        .load(uri)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                GlideApp.with(imageView.getContext()).clear(imageView);
            }
        });

        Once.initialise(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
