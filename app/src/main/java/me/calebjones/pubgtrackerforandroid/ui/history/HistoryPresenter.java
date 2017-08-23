package me.calebjones.pubgtrackerforandroid.ui.history;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;

import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;

public class HistoryPresenter extends BasePresenter implements HistoryContract.Presenter {

    private final HistoryContract.View historyView;
    private User currentUser;
    private DataManager dataManager;

    public HistoryPresenter(HistoryContract.View view){
        historyView = view;
        historyView.setPresenter(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        historyView.setRefreshEnabled(true);
        currentUser = userSelected.response;
        updateAdapter(currentUser);
    }

    private void updateAdapter(User user) {
        RealmResults<Match> matches = retrieveMatchHistory(user.getPubgTrackerId());
        if (matches.size() > 0) {
            historyView.setAdapterMatches(matches);
            historyView.setViewStateContent();
        } else {
            historyView.setViewStateEmpty();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onRefreshEventReceiver(UserRefreshing state) {
        historyView.setRefreshing(state.refreshing);
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
    public void retrieveCachedUser() {
        currentUser = getRealm().where(User.class).equalTo("defaultUser", true).findFirst();
        if (currentUser != null) {
            updateAdapter(currentUser);
        } else {
            historyView.setViewStateEmpty();
        }
    }

    @Override
    public void refreshCurrentUser() {

    }

    @Override
    public RealmResults<Match> retrieveMatchHistory(int pubgTrackerId) {
        String region = historyView.getRegion(historyView.getRegionFilter());
        String season = historyView.getSeason(historyView.getSeasonFilter());
        String mode = historyView.getMode(historyView.getModeFilter());

        RealmQuery<Match> matchRealmQuery = getRealm().where(Match.class)
                .equalTo("users.pubgTrackerId", pubgTrackerId);

        if (!region.equals("All")){
            matchRealmQuery.contains("regionDisplay", region);
        }

        if (!season.equals("All")){
            matchRealmQuery.equalTo("seasonDisplay", season);
        }

        if (!mode.equals("All")){
            matchRealmQuery.contains("matchDisplay", mode);
        }

        RealmResults<Match> matches = matchRealmQuery.findAll();

        for (Match match: matches){
            Timber.v("%s - %s - %s",
                    match.getMatchDisplay(),
                    match.getRegionDisplay(),
                    match.getSeasonDisplay());
        }

        return matchRealmQuery.findAll();

    }

    @Override
    public void sortSubmitClicked() {
        updateAdapter(currentUser);
    }

    @Override
    public void resetClicked() {
        historyView.resetFilters();
        updateAdapter(currentUser);
    }
}
