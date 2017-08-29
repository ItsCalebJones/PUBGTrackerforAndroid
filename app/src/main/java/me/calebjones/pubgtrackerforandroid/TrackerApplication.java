package me.calebjones.pubgtrackerforandroid;

import android.app.Application;
import android.content.ContextWrapper;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import me.calebjones.pubgtrackerforandroid.utils.GlideApp;
import timber.log.Timber;

public class TrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        DataClient.create();
        Timber.plant(new Timber.DebugTree());
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

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
    }
}
