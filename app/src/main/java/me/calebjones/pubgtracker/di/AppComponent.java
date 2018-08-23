package me.calebjones.pubgtracker.di;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import me.calebjones.pubgtracker.TrackerApplication;

@Singleton
@Component(modules = {
        AppModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TrackerApplication application);
        AppComponent build();
    }
    void inject(TrackerApplication app);
}