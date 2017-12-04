package me.calebjones.pubgtracker.data.events;

import timber.log.Timber;

/**
 * Created by ALPCJONESM2 on 12/4/17.
 */

public class MatchRefreshing {

    public final boolean refreshing;

    public MatchRefreshing(boolean refreshing){
        Timber.d("EventBus - MatchRefreshing - Refreshing: %s", refreshing);
        this.refreshing = refreshing;
    }
}
