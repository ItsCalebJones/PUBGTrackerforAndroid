package me.calebjones.pubgtrackerforandroid.data.events;

import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;

public class UserFavoriteEvent {

    public final User user;

    public UserFavoriteEvent(User user) {
        Timber.d("EventBust - UserFavoriteEvent - %s", user.getPlayerName());
        this.user = user;
    }
}
