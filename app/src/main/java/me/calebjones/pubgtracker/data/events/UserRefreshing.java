package me.calebjones.pubgtracker.data.events;

import timber.log.Timber;

/**
 * Created by Caleb on 8/18/2017.
 */

public class UserRefreshing {

    public final boolean refreshing;

    public UserRefreshing(boolean refreshing){
        Timber.d("EventBus - UserRefreshing - Refreshing: %s", refreshing);
        this.refreshing = refreshing;
    }
}
