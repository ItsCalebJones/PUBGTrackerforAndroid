package me.calebjones.pubgtracker.ui.history;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import me.calebjones.pubgtracker.common.BasePresenter;
import me.calebjones.pubgtracker.data.DataManager;
import me.calebjones.pubgtracker.data.enums.PUBGMode;
import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.enums.PUBGSeason;
import me.calebjones.pubgtracker.data.events.MatchResults;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.User;
import timber.log.Timber;

public class HistoryPresenter extends BasePresenter implements HistoryContract.Presenter {

    private final HistoryContract.View historyView;
    private User currentUser;
    private DataManager dataManager;
    private RealmResults<Match> matches;

    public HistoryPresenter(HistoryContract.View view) {
        Timber.v("Creating HistoryPresenter with View.");
        historyView = view;
        historyView.setPresenter(this);
        dataManager = DataManager.getInstance();
        Timber.v("Creating HistoryPresenter complete.");
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
        if (matches != null) {
            matches.removeAllChangeListeners();
        }
    }

    @Override
    public void onResume() {
        Timber.v("onResume");
        registerEventBus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        currentUser = userSelected.user;
        Timber.i("onUserSelectedEventReceived - EventBus - Message received - User: %s", currentUser.getPlayerName());
        updateAdapter(currentUser);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMatchResultReceived(MatchResults results) {
        currentUser = results.user;
        Timber.i("onMatchResultReceived - EventBus - Message received - User: %s", currentUser.getPlayerName());
        updateAdapter(currentUser);
    }

    private void updateAdapter(User user) {
        Timber.d("updateAdapter - Retrieving match history.");
        matches = retrieveMatchHistory(user);
        if (matches.size() > 0) {
            Timber.v("updateAdapter RealmChangeListener - Found %s matches - Adding to adapter.", matches.size());
            if (matches.size() > 25){
                matches = matches.
            }
            historyView.setAdapterMatches(matches);
            historyView.showContent();
        } else {
            Timber.v("updateAdapter RealmChangeListener - Found no matches - Showing empty view.");
            historyView.showEmpty();
        }
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
        currentUser = dataManager.getCurrentUser();
        if (currentUser != null) {
            Timber.v("retrieveCachedUser - Default user found, updating view.");
            updateAdapter(currentUser);
        } else {
            Timber.v("retrieveCachedUser - No Default user found, updating view state to empty.");
            historyView.showNoUser();
        }
    }

    @Override
    public RealmResults<Match> retrieveMatchHistory(final User user) {
        Timber.d("retrieveMatchHistory - Retrieving Match History...");
        String region = historyView.getRegion(historyView.getRegionFilter());
        String season = historyView.getSeason(historyView.getSeasonFilter());
        String mode = historyView.getMode(historyView.getModeFilter());
        Timber.v("retrieveMatchHistory - Region: %s - Season: %s - Mode: %s", region, season, mode);

        RealmQuery<Match> matchRealmQuery = getRealm().where(Match.class)
                .equalTo("users.accountId", user.getAccountId());

        if (!region.equals(PUBGRegion.AAG.getRegionName())) {
            matchRealmQuery.contains("regionDisplay", region);
        }

        if (!season.equals(PUBGSeason.ALL.getSeasonName())) {
            matchRealmQuery.equalTo("seasonDisplay", season);
        }

        if (!mode.equals(PUBGMode.ALL.getModeName())) {
            matchRealmQuery.contains("matchDisplay", mode);
        }

        return matchRealmQuery.findAllSorted("updated", Sort.DESCENDING);
    }

    @Override
    public void sortSubmitClicked() {
        Timber.d("sortSubmitClicked - Submit button click.");
        if (currentUser != null) {
            updateAdapter(currentUser);
        }
    }

    @Override
    public void resetClicked() {
        Timber.d("resetClicked - reseting filters and updating adapter.");
        historyView.resetFilters();
        updateAdapter(currentUser);
    }


}
