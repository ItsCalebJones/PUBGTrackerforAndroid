package me.calebjones.pubgtrackerforandroid.ui.history;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
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
    private RealmResults<Match> matches;

    public HistoryPresenter(HistoryContract.View view){
        historyView = view;
        historyView.setPresenter(this);
        dataManager = new DataManager();
    }

    @Override
    public void onStart() {
        Timber.v("onStart");
        registerEventBus();
        matches.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Match>>() {
            @Override
            public void onChange(RealmResults<Match> matches, OrderedCollectionChangeSet changeSet) {
                if (matches.size() > 0) {
                    Timber.v("Found %s matches - Adding to adapter.", matches.size());
                    historyView.setAdapterMatches(matches);
                    historyView.setViewStateContent();
                } else {
                    Timber.v("Found no matches - Showing empty view.");
                    historyView.setViewStateEmpty();
                }
            }
        });
    }

    @Override
    public void onStop() {
        Timber.v("onStop");
        unRegisterEventBus();
        matches.removeAllChangeListeners();
    }

    @Override
    public void onResume() {
        Timber.v("onResume");
        registerEventBus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        Timber.v("onUserEventReceived - EventBus - Message received - User: %", userSelected.response.getPlayerName());
        historyView.setRefreshEnabled(true);
        currentUser = userSelected.response;
        updateAdapter(currentUser);
    }

    private void updateAdapter(User user) {
        matches = retrieveMatchHistory(user.getPubgTrackerId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onRefreshEventReceiver(UserRefreshing state) {
        Timber.v("onRefreshEventReceiver - EventBus - Message received - Refreshing: %", state.refreshing);
        historyView.setRefreshing(state.refreshing);
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

    @Override
    public void retrieveCachedUser() {
        Timber.v("retrieveCachedUser - Retrieving cached user...");
        currentUser = getRealm().where(User.class).equalTo("defaultUser", true).findFirst();
        if (currentUser != null) {
            Timber.v("retrieveCachedUser - Default user found, updating view.");
            updateAdapter(currentUser);
        } else {
            Timber.v("retrieveCachedUser - No Default user found, updating view state to empty.");
            historyView.setViewStateEmpty();
        }
    }

    //Todo
    @Override
    public void refreshCurrentUser() {

    }

    @Override
    public RealmResults<Match> retrieveMatchHistory(int pubgTrackerId) {
        Timber.v("retrieveMatchHistory - Retrieving Match History...");
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

        return matchRealmQuery.findAllSortedAsync("updated", Sort.DESCENDING);

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
