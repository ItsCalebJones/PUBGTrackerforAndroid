package me.calebjones.pubgtracker.ui.main;


import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.Realm;
import io.realm.RealmResults;
import me.calebjones.pubgtracker.common.BasePresenter;
import me.calebjones.pubgtracker.data.DataManager;
import me.calebjones.pubgtracker.data.events.UserFavoriteEvent;
import me.calebjones.pubgtracker.data.events.UserRefreshing;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private final MainContract.View mainView;
    private MainContract.Navigator homeNavigator;
    private DataManager dataManager;
    private User currentUser;

    public MainPresenter(MainContract.View view){
        mainView = view;
        mainView.setPresenter(this);
        dataManager = DataManager.getInstance();
    }

    public void setNavigator(@NonNull MainContract.Navigator navigator) {
        homeNavigator = navigator;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserSelectedEventReceived(UserSelected userSelected) {
        Timber.d("UserSelected Event Received.");
        setCurrentUser();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserRefreshingEventReceived(UserRefreshing userRefreshing) {
        Timber.d("UserRefreshed Event Received.");
        boolean refreshing = userRefreshing.refreshing;
        mainView.setRefreshing(refreshing);
    }


    @Override
    public boolean searchQuerySubmitted(final String query) {
        sendRefreshingState(true);
        getUserByName(query, null);
        mainView.closeSearchView();
        return true;
    }

    private void getUserByName(String query, final User user) {
        Timber.v("Searching for %s...", query);
        EventBus.getDefault().post(new UserRefreshing(true));
        dataManager.updateUserByProfileName(query, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null){
                            mainView.createErrorSnackbar(user.getError());
                        } else if (user.getMessage() != null) {
                            mainView.createErrorSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            dataManager.getDataSaver().saveUser(user);
                            dataManager.getUserMatchHistory(user);
                            currentUser = user;
                            mainView.setActiveUser(user);
                        }
                    }
                } else {
                    mainView.setRefreshing(false);
                    mainView.createErrorSnackbar(response.message());
                    if (user != null){
                        currentUser = user;
                        mainView.setActiveUser(user);
                    }
                    Timber.e(response.message());
                }
                sendRefreshingState(false);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                mainView.createSnackbar(t.getLocalizedMessage());
                mainView.setRefreshing(false);
                sendRefreshingState(false);
                if (user != null){
                    currentUser = user;
                    mainView.setActiveUser(user);
                }
            }
        });
    }

    private void sendRefreshingState(boolean state) {
        EventBus.getDefault().post(new UserRefreshing(state));
    }

    private void sendUserToEventBus(User user) {
        EventBus.getDefault().post(new UserSelected(user));
    }


    @Override
    public void goOverviewClicked() {
        homeNavigator.goOverview();
    }

    @Override
    public void goMatchHistoryClicked() {
        homeNavigator.goMatchHistory();
    }

    @Override
    public void goStatisticsClicked() {
        homeNavigator.goStatistics();
    }

    @Override
    public void setUserAsCurrent(final User newCurrentUser) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> users = realm.where(User.class).equalTo("currentUser", true).findAll();
                for (User newCurrentUser: users){
                    newCurrentUser.setCurrentUser(false);
                    realm.copyToRealmOrUpdate(newCurrentUser);
                }
                newCurrentUser.setCurrentUser(true);
                realm.copyToRealmOrUpdate(newCurrentUser);
                sendUserToEventBus(newCurrentUser);
            }
        });
    }

    @Override
    public void goMapClicked() {
        homeNavigator.goMapActivity();
    }

    @Override
    public void goCompareClicked() {
        homeNavigator.goCompareActivity();
    }

    @Override
    public void getUsers() {
        mainView.setUsers(dataManager.getSavedUsers());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserFavoriteEvent(UserFavoriteEvent userFavoriteEvent) {
        User user = userFavoriteEvent.user;
        if (user.isFavoriteUser()){
            mainView.addDrawerUser(user);
        } else {
            mainView.deleteUser(user);
        }
    }

    @Override
    public void setCurrentUser() {
        currentUser = dataManager.getCurrentUser();
        mainView.setActiveUser(currentUser);
    }

    @Override
    public void setCurrentUser(long identifier) {
        User user = dataManager.getUserByID((int) identifier);
        getUserByName(user.getPlayerName(), user);

    }

    @Override
    public void goToSettings() {
        homeNavigator.goToSettings();
    }

    @Override
    public void setFavoriteUserState(final boolean currentUserState) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                currentUser.setFavoriteUser(currentUserState);
                realm.copyToRealmOrUpdate(currentUser);
                EventBus.getDefault().post(new UserFavoriteEvent(currentUser));
            }
        });
    }

    @Override
    public void refreshCurrentUser() {
        dataManager.updateUserByProfileName(currentUser.getPlayerName(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null && user.getMessage() != null) {
                            mainView.createSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            dataManager.getDataSaver().saveUser(user);
                            dataManager.getUserMatchHistory(user);
                        }
                    }
                } else {
                    mainView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                mainView.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                mainView.createSnackbar(t.getLocalizedMessage());
                mainView.setRefreshing(false);
            }
        });
    }


    public void registerEventBus() {
        Timber.v("Checking EventBus...");
        if (!EventBus.getDefault().isRegistered(this)) {
            Timber.v("Registering EventBus");
            EventBus.getDefault().register(this);
        }
    }

    public void unRegisterEventBus() {
        Timber.v("Unregistering EventBus");
        EventBus.getDefault().unregister(this);
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
