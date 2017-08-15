package me.calebjones.pubgtrackerforandroid.home;


import android.support.annotation.NonNull;
import android.support.v7.app.WindowDecorActionBar;

import org.greenrobot.eventbus.EventBus;

import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.APIResponse;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter implements MainContract.Presenter {

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
    public boolean searchQuerySubmitted(String query) {
        DataClient.getInstance().getProfileByName(query, new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()){
                    APIResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        if (apiResponse.getError() != null && apiResponse.getMessage() != null) {
                            homeView.createSnackbar(apiResponse.getMessage());
                        } else if (apiResponse.getPlayerName() != null) {
                            EventBus.getDefault().post(new UserSelected(apiResponse));
                        }
                    }
                } else {
                    homeView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                homeView.closeSearchView();
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Timber.e(t);
                homeView.createSnackbar(t.getLocalizedMessage());
                homeView.closeSearchView();
            }
        });
        return true;
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
