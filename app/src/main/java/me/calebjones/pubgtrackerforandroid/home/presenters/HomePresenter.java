package me.calebjones.pubgtrackerforandroid.home.presenters;

import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.home.contracts.HomeContract;
import timber.log.Timber;


public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private final HomeContract.View homeView;

    public HomePresenter(HomeContract.View view) {
        homeView = view;
        homeView.setPresenter(this);

    }

    @Override
    public void applyUser(User user) {
        homeView.setProfileAvatar(user.getAvatar());
        homeView.setProfileName(user.getPlayerName());
        homeView.setCurrentRating(String.valueOf(Math.round(user.getMatchHistory().first().getRating())));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onMessageReceived(UserSelected userSelected) {
        applyUser(userSelected.response);
    }

    @Override
    public void registerEventBus() {
        Timber.v("Checking EventBus...");
        if (!EventBus.getDefault().isRegistered(this)) {
            Timber.v("Registering EventBus");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void unRegisterEventBus() {
        Timber.v("Unregistering EventBus");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setInformationCardDismissed(boolean state) {
        Prefs.putBoolean(Config.PREF_INFORMATION_CARD_DISMISSED, state);
    }

    @Override
    public void retrieveCachedUser() {
        User user = getRealm().where(User.class).findFirst();
        if (user != null){
            applyUser(user);
        }
    }

    @Override
    public void onStart() {
        Timber.v("onStart");
        registerEventBus();
    }

    @Override
    public void onStop() {
        Timber.v("onStop");
        unRegisterEventBus();
    }

    @Override
    public void onResume() {
        Timber.v("onResume");
        registerEventBus();
    }
}
