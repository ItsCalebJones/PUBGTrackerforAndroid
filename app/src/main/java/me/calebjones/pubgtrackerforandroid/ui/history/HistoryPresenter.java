package me.calebjones.pubgtrackerforandroid.ui.history;

import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.models.User;

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

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }
}
