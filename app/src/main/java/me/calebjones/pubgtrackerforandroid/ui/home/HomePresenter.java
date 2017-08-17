package me.calebjones.pubgtrackerforandroid.ui.home;

import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Objects;
import java.util.Timer;

import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.data.models.Stats;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;


public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private final HomeContract.View homeView;

    public HomePresenter(HomeContract.View view) {
        homeView = view;
        homeView.setPresenter(this);

    }

    @Override
    public void applyUser(User user) {
        PlayerStat highestElo = null;
        PlayerStat secondElo = null;
        for (PlayerStat playerStat : user.getStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    !Objects.equals(playerStat.getRegion(), "agg")) {
                float elo = playerStat.getStats().get(9).getValueDec();
                if (highestElo == null) {
                    highestElo = playerStat;
                } else if (highestElo.getStats().get(9).getValueDec() < elo) {
                    secondElo = highestElo;
                    highestElo = playerStat;
                } else if (secondElo == null) {
                    secondElo = playerStat;
                } else if (secondElo.getStats().get(9).getValueDec() < elo) {
                    secondElo = playerStat;
                }
            }
        }
        if (highestElo != null) {
            homeView.setOverviewSeasonOne(highestElo);
        }
        if (secondElo != null) {
            homeView.setOverviewSeasonTwo(secondElo);
        }
        homeView.setProfileAvatar(user.getAvatar());
        homeView.setProfileName(user.getPlayerName());
        homeView.setCurrentRatingAndRank(
                highestElo.getStats().get(9).getValue(),
                String.valueOf(highestElo.getStats().get(9).getRank()),
                findKD(user));
    }

    private String findKD(User user) {
        Float highestKD = null;
        for (PlayerStat playerStat : user.getStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    Objects.equals(playerStat.getRegion(), "agg")) {
                float currentKd = playerStat.getStats().get(0).getValueDec();
                if (highestKD == null) {
                    highestKD = currentKd;
                } else if (highestKD < currentKd) {
                    highestKD = currentKd;
                }
            }
        }
        if (highestKD != null) {
            return String.valueOf(highestKD);
        } else {
            return "Unknown";
        }
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
        if (user != null) {
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
