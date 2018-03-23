package me.calebjones.pubgtracker.data.events;


import me.calebjones.pubgtracker.data.models.tracker.User;
import timber.log.Timber;

public class UserSelected {

    public final User user;

    public UserSelected(User user){
        Timber.d("EventBust - UserSelected - User: %s", user.getPlayerName());
        this.user = user;
    }
}
