package me.calebjones.pubgtrackerforandroid.home;


import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter extends BasePresenter implements MainContract.Presenter {

    private final MainContract.View homeView;
    private MainContract.Navigator homeNavigator;

    public MainPresenter(MainContract.View view){
        homeView = view;
        homeView.setPresenter(this);
    }

    public void setNavigator(@NonNull MainContract.Navigator navigator) {
        homeNavigator = navigator;
    }

    @Override
    public boolean searchQuerySubmitted(final String query) {
        DataClient.getInstance().getProfileByName(query, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null && user.getMessage() != null) {
                            homeView.createSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            saveToRealm(user);
                            EventBus.getDefault().post(new UserSelected(user));
                        }
                    }
                } else {
                    homeView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                homeView.closeSearchView();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                homeView.createSnackbar(t.getLocalizedMessage());
                homeView.closeSearchView();
            }
        });
        return true;
    }
    private void saveToRealm(final User user) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });

    }

    @Override
    public void goHomeClicked() {
        homeNavigator.goHome();
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
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }
}
