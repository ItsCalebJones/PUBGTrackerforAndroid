package me.calebjones.pubgtracker.ui.overview;

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import jonathanfinerty.once.Once;
import me.calebjones.pubgtracker.common.BasePresenter;
import me.calebjones.pubgtracker.data.Config;
import me.calebjones.pubgtracker.data.DataManager;
import me.calebjones.pubgtracker.data.events.UserRefreshing;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.User;
import me.calebjones.pubgtracker.data.models.PlayerStat;
import timber.log.Timber;


public class OverviewPresenter extends BasePresenter implements OverviewContract.Presenter {

    private final OverviewContract.View overviewView;
    private DataManager dataManager;
    private OverviewContract.Navigator navigator;

    public OverviewPresenter(OverviewContract.View view) {
        overviewView = view;
        overviewView.setPresenter(this);
        dataManager = DataManager.getInstance();
    }

    public void setNavigator(@NonNull OverviewContract.Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void applyUser(User user) {
        configureOverviewCard(user);
        configureMatchCard(user);
    }

    private void configureMatchCard(User user) {
        if (user.getMatchHistory().size() > 0) {
            overviewView.setMatchCardVisible(true);
            overviewView.setMatchCardContent(user.getMatchHistory().get(0));
        }
    }

    private void configureOverviewCard(User user) {
        overviewView.setOverviewCardVisible(true);
        PlayerStat highestElo = null;
        for (PlayerStat playerStat : user.getPlayerStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    !Objects.equals(playerStat.getRegion(), "agg")) {
                float elo = playerStat.getStats().get(9).getValueDec();
                if (highestElo == null) {
                    highestElo = playerStat;
                } else if (highestElo.getStats().get(9).getValueDec() < elo) {
                    highestElo = playerStat;
                }
            }
            if (highestElo != null) {
                overviewView.setOverviewContent(highestElo);
            } else {
                overviewView.setOverviewSeasonOneVisible(false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        Timber.v("onUserSelectedEventReceived - UserSelected event.");
        overviewView.showContent();
        applyUser(userSelected.user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onRefreshEventReceiver(UserRefreshing state) {
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
        Once.markDone(Config.SHOW_WELCOME_CARD);
    }

    @Override
    public void retrieveCachedUser() {
        User user = dataManager.getCurrentUser();
        if (user != null) {
            applyUser(user);
        } else {
            overviewView.showNoUser();
        }
    }

    @Override
    public void showIntro() {
        navigator.showIntro();
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
