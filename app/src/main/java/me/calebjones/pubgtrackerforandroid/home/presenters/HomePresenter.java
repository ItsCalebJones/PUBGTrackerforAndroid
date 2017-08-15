package me.calebjones.pubgtrackerforandroid.home.presenters;

import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.home.contracts.HomeContract;
import timber.log.Timber;


public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View homeView;

    public HomePresenter(HomeContract.View view) {
        homeView = view;
        homeView.setPresenter(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onMessageReceived(UserSelected userSelected) {
        homeView.setProfileAvatar(userSelected.response.getAvatar());
        homeView.setProfileName(userSelected.response.getPlayerName());
        homeView.setCurrentRating(String.valueOf(Math.round(userSelected.response.getMatchHistory().first().getRating())));
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
