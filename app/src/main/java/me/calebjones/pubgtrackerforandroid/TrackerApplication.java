package me.calebjones.pubgtrackerforandroid;

import android.app.Application;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import me.calebjones.pubgtrackerforandroid.utils.GlideApp;
import me.calebjones.pubgtrackerforandroid.utils.SplashScreenHelper;
import timber.log.Timber;

public class TrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new SplashScreenHelper());
        Realm.init(this);
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


}
