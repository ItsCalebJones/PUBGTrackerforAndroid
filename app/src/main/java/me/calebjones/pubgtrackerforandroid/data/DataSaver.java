package me.calebjones.pubgtrackerforandroid.data;

import com.bumptech.glide.request.target.ThumbnailImageViewTarget;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;


public class DataSaver {

    private Realm realm;

    public DataSaver() {
        realm = Realm.getDefaultInstance();
    }

    public void save(final User user) {
        Timber.i("save - Saving user %s", user.getPlayerName());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User cachedUser = realm.where(User.class).equalTo("pubgTrackerId", user.getPubgTrackerId()).findFirst();
                if (cachedUser != null && cachedUser.isFavoriteUser()){
                    Timber.d("save -executeTransaction - %s is favorite.", user.getPlayerName());
                    user.setFavoriteUser(true);
                }
                User currentUser = realm.where(User.class).equalTo("currentUser", true).findFirst();

                if (currentUser != null) {
                    currentUser.setCurrentUser(false);
                    realm.copyToRealmOrUpdate(currentUser);
                }

                user.setCurrentUser(true);
                realm.copyToRealmOrUpdate(user);
            }
        });
        EventBus.getDefault().post(new UserRefreshing(false));
        EventBus.getDefault().post(new UserSelected(user));
    }
}
