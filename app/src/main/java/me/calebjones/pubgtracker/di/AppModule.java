package me.calebjones.pubgtracker.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.calebjones.pubgtracker.TrackerApplication;

@Module
public class AppModule {

    @Provides
    Context provideContext(TrackerApplication application) {
        return application.getApplicationContext();
    }
}