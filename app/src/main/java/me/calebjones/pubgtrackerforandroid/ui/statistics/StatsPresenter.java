package me.calebjones.pubgtrackerforandroid.ui.statistics;

import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.models.User;



public class StatsPresenter extends BasePresenter implements StatsContract.Presenter {

    private final StatsContract.View statsView;
    private User currentUser;
    private DataManager dataManager;

    public StatsPresenter(StatsContract.View view){
        statsView = view;
        statsView.setPresenter(this);
        dataManager = new DataManager();
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
