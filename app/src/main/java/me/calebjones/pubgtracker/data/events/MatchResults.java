package me.calebjones.pubgtracker.data.events;

import me.calebjones.pubgtracker.data.models.tracker.User;
import timber.log.Timber;

/**
 * Created by ALPCJONESM2 on 12/4/17.
 */

public class MatchResults {

    public final User user;

    public MatchResults(User user){
        Timber.d("EventBust - UserSelected - User: %s", user.getPlayerName());
        this.user = user;
    }
}
