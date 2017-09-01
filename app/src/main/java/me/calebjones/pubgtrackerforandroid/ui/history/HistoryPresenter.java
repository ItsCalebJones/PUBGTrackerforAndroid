package me.calebjones.pubgtrackerforandroid.ui.history;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGMode;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGRegion;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HistoryPresenter extends BasePresenter implements HistoryContract.Presenter {

    private final HistoryContract.View historyView;
    private User currentUser;
    private DataManager dataManager;
    private RealmResults<Match> matches;

    public HistoryPresenter(HistoryContract.View view){
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
        if (matches != null){
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
        Timber.i("onUserEventReceived - EventBus - Message received - User: %s", currentUser.getPlayerName());
        historyView.setRefreshEnabled(true);
        historyView.showContent();
        updateAdapter(currentUser);
    }

    private void updateAdapter(User user) {
        Timber.d("updateAdapter - Retrieving match history.");
        matches = retrieveMatchHistory(user.getPubgTrackerId());
        matches.addChangeListener(new RealmChangeListener<RealmResults<Match>>() {
            @Override
            public void onChange(RealmResults<Match> matches) {
                if (matches.size() > 0) {
                    Timber.v("updateAdapter RealmChangeListener - Found %s matches - Adding to adapter.", matches.size());
                    historyView.setAdapterMatches(matches);
                    historyView.showContent();
                } else {
                    Timber.v("updateAdapter RealmChangeListener - Found no matches - Showing empty view.");
                    historyView.showEmpty();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onRefreshEventReceiver(UserRefreshing state) {
        Timber.i("onRefreshEventReceiver - EventBus - Message received - Refreshing: %s", state.refreshing);
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
        currentUser = dataManager.getCurrentUser();
        if (currentUser != null) {
            Timber.v("retrieveCachedUser - Default user found, updating view.");
            updateAdapter(currentUser);
        } else {
            Timber.v("retrieveCachedUser - No Default user found, updating view state to empty.");
            historyView.showNoUser();
        }
    }

    //Todo
    @Override
    public void refreshCurrentUser() {
        Timber.i("refreshCurrentUser - refreshing %s matches.", currentUser.getPlayerName());
        dataManager.updateUserByProfileName(currentUser.getPlayerName(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Timber.d("refreshCurrentUser - onResponse - Successful: %s", response.isSuccessful());
                if (response.isSuccessful()){
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null && user.getMessage() != null) {
                            Timber.e("refreshCurrentUser - onResponse - Error: %s", user.getMessage());
                            historyView.createSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            Timber.d("refreshCurrentUser - onResponse - Saving user data.");
                            dataManager.getDataSaver().save(user);
                        }
                    }
                } else {
                    Timber.e("refreshCurrentUser - onResponse - Error: %s", response.message());
                    historyView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                historyView.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                Timber.e("refreshCurrentUser - onFailure - Error: %s", t.getLocalizedMessage());
                historyView.createSnackbar(t.getLocalizedMessage());
                historyView.setRefreshing(false);
            }
        });
    }

    @Override
    public RealmResults<Match> retrieveMatchHistory(int pubgTrackerId) {
        Timber.d("retrieveMatchHistory - Retrieving Match History...");
        String region = historyView.getRegion(historyView.getRegionFilter());
        String season = historyView.getSeason(historyView.getSeasonFilter());
        String mode = historyView.getMode(historyView.getModeFilter());
        Timber.v("retrieveMatchHistory - Region: %s - Season: %s - Mode: %s", region, season, mode);

        RealmQuery<Match> matchRealmQuery = getRealm().where(Match.class)
                .equalTo("users.pubgTrackerId", pubgTrackerId);

        if (!region.equals(PUBGRegion.AAG.getRegionName())){
            matchRealmQuery.contains("regionDisplay", region);
        }

        if (!season.equals(PUBGSeason.ALL.getSeasonName())){
            matchRealmQuery.equalTo("seasonDisplay", season);
        }

        if (!mode.equals(PUBGMode.ALL.getModeName())){
            matchRealmQuery.contains("matchDisplay", mode);
        }

        return matchRealmQuery.findAllSortedAsync("updated", Sort.DESCENDING);

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

    @Override
    public void checkHint() {
        if (currentUser != null){
            historyView.showFilterHint();
        }
    }
}
