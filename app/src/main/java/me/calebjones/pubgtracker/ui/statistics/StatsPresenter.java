package me.calebjones.pubgtracker.ui.statistics;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.calebjones.pubgtracker.data.DataManager;
import me.calebjones.pubgtracker.common.BasePresenter;
import me.calebjones.pubgtracker.data.enums.PUBGMode;
import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.enums.PUBGSeason;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.PlayerStat;
import me.calebjones.pubgtracker.data.models.User;
import timber.log.Timber;


public class StatsPresenter extends BasePresenter implements StatsContract.Presenter {

    private final StatsContract.View statsView;
    private User currentUser;
    private DataManager dataManager;

    public StatsPresenter(StatsContract.View view) {
        statsView = view;
        statsView.setPresenter(this);
        dataManager = DataManager.getInstance();
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
        Timber.v("onUserSelectedEventReceived - UserSelected event.");
        currentUser = userSelected.user;
        applyUser(currentUser);
    }

    @Override
    public void retrieveCachedUser() {
        currentUser = dataManager.getCurrentUser();
        applyUser(currentUser);
    }

    private void applyUser(User currentUser) {
        if (currentUser != null) {
            statsView.showContent();
            List<PUBGRegion> regionList = Arrays.asList(PUBGRegion.values());
            List<PUBGSeason> seasonList = new ArrayList<>();
            List<String> modeList = new ArrayList<>();

            seasonList.add(PUBGSeason.PRE5_2017);
            seasonList.add(PUBGSeason.PRE4_2017);
            seasonList.add(PUBGSeason.PRE3_2017);
            seasonList.add(PUBGSeason.PRE2_2017);
            seasonList.add(PUBGSeason.PRE1_2017);

            modeList.add("First Person Perspective");
            modeList.add("Third Person Perspective");

            String region = regionList.get(statsView.getRegionFilter()).getKeyName();
            String season = seasonList.get(statsView.getSeasonFilter()).getSeasonKey();

            String solo;
            String duo;
            String squad;
            if (statsView.getViewModeFilter() == 1){
                solo = PUBGMode.SOLO.getKeyName();
                duo = PUBGMode.DUO.getKeyName();
                squad = PUBGMode.SQUAD.getKeyName();
            } else {
                solo = PUBGMode.FPP_SOLO.getKeyName();
                duo = PUBGMode.FPP_DUO.getKeyName();
                squad = PUBGMode.FPP_SQUAD.getKeyName();
            }

            PlayerStat soloResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", solo).findFirst();

            PlayerStat duoResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", duo).findFirst();

            PlayerStat squadResult = getRealm().where(PlayerStat.class)
                    .equalTo("users.pubgTrackerId", currentUser.getPubgTrackerId())
                    .contains("region", region)
                    .equalTo("season", season)
                    .equalTo("match", squad).findFirst();

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

    private void updateUser() {
        retrieveCachedUser();
    }
}
