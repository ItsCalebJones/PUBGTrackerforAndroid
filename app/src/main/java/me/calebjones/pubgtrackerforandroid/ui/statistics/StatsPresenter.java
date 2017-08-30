package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGMode;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGRegion;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;


public class StatsPresenter extends BasePresenter implements StatsContract.Presenter {

    private final StatsContract.View statsView;
    private User currentUser;
    private DataManager dataManager;

    public StatsPresenter(StatsContract.View view) {
        statsView = view;
        statsView.setPresenter(this);
        dataManager = new DataManager();
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

    @Override
    public void registerEventBus() {
        Timber.v("registerEventBus - Checking EventBus...");
        if (!EventBus.getDefault().isRegistered(this)) {
            Timber.v("registerEventBus - Registering EventBus");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void unRegisterEventBus() {
        Timber.v("unRegisterEventBus - Unregistering EventBus");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        retrieveCachedUser();
    }

    @Override
    public void retrieveCachedUser() {
        currentUser = dataManager.getCurrentUser();
        if (currentUser != null) {
            statsView.showContent();
            List<PUBGRegion> regionList = Arrays.asList(PUBGRegion.values());
            List<PUBGSeason> seasonList = new ArrayList<>();

            seasonList.add(PUBGSeason.PRE3_2017);
            seasonList.add(PUBGSeason.PRE2_2017);
            seasonList.add(PUBGSeason.PRE1_2017);

            String region = regionList.get(statsView.getRegionFilter()).getKeyName();
            String season = seasonList.get(statsView.getSeasonFilter()).getSeasonKey();

            PlayerStat soloResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", PUBGMode.SOLO.getKeyName()).findFirst();

            PlayerStat duoResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", PUBGMode.DUO.getKeyName()).findFirst();

            PlayerStat squadResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", PUBGMode.SQUAD.getKeyName()).findFirst();

            if (soloResult != null) {
                statsView.configureSoloPlaylist(soloResult);
                statsView.soloVisibility(View.VISIBLE);
            } else {
                statsView.soloVisibility(View.GONE);
            }

            if (duoResult != null) {
                statsView.configureDuoPlaylist(duoResult);
                statsView.duoVisibility(View.VISIBLE);
            } else {
                statsView.duoVisibility(View.GONE);
            }

            if (squadResult != null) {
                statsView.configureSquadPlaylist(squadResult);
                statsView.squadVisibility(View.VISIBLE);
            } else {
                statsView.squadVisibility(View.GONE);
            }

            if (soloResult == null && duoResult == null && squadResult == null){
                statsView.showEmpty();
            }
        } else

        {
            statsView.showNoUser();
        }

    }

    @Override
    public void sortSubmitClicked() {
        Timber.d("sortSubmitClicked - Submit button click.");
        if (currentUser != null) {
            updateUser();
        }
    }

    @Override
    public void resetClicked() {
        Timber.d("resetClicked - reseting filters and updating adapter.");
        statsView.resetFilters();
        updateUser();
    }

    @Override
    public void checkHint() {
        statsView.checkHint();
    }

    private void updateUser() {
        retrieveCachedUser();
    }
}
