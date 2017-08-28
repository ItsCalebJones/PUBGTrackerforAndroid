package me.calebjones.pubgtrackerforandroid.ui.main;


import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private final MainContract.View mainView;
    private MainContract.Navigator homeNavigator;
    private DataManager dataManager;

    public MainPresenter(MainContract.View view){
        mainView = view;
        mainView.setPresenter(this);
        dataManager = new DataManager();
    }

    public void setNavigator(@NonNull MainContract.Navigator navigator) {
        homeNavigator = navigator;
    }

    @Override
    public boolean searchQuerySubmitted(final String query) {
        sendRefreshingState(true);
        dataManager.getUserByProfileName(query, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null && user.getMessage() != null) {
                            mainView.createSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            dataManager.getDataSaver().save(user);
                            mainView.setDrawerUser(user);
                            mainView.createSnackbarSetCurrentUser(
                                    String.format("Set %s as default user?",
                                    user.getPlayerName()),
                                    user);
                            sendUserToEventBus(user);
                        }
                    }
                } else {
                    mainView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                sendRefreshingState(false);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                mainView.createSnackbar(t.getLocalizedMessage());

                sendRefreshingState(false);
            }
        });
        mainView.closeSearchView();
        return true;
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
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }
}
