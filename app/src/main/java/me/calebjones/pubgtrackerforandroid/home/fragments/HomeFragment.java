package me.calebjones.pubgtrackerforandroid.home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.calebjones.pubgtrackerforandroid.home.presenters.HomePresenter;
import me.calebjones.pubgtrackerforandroid.home.views.HomeViewImpl;
import timber.log.Timber;


public class HomeFragment extends Fragment {

    private HomePresenter homePresenter;
    private HomeViewImpl homeView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        homeView = new HomeViewImpl(getContext(), inflater, container);
        homePresenter = new HomePresenter(homeView);
        homePresenter.registerEventBus();
        return homeView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.v("onStart");
        homePresenter.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.v("onResume");
        homePresenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.v("onStop");
        homePresenter.onStop();
    }
}
