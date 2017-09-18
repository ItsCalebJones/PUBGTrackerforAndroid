package me.calebjones.pubgtrackerforandroid.ui.map;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BasePresenter;

public class MapPresenter extends BasePresenter implements MapContract.Presenter {

    private final MapContract.View mainView;
    private MapContract.Navigator mapNavigator;

    public MapPresenter(MapContract.View view){
        mainView = view;
        mainView.setPresenter(this);
    }

    public void setNavigator(@NonNull MapContract.Navigator navigator) {
        mapNavigator = navigator;
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
