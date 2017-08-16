package me.calebjones.pubgtrackerforandroid.home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.easyprefs.library.Prefs;

import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.home.presenters.HomePresenter;
import me.calebjones.pubgtrackerforandroid.home.views.HomeViewImpl;
import timber.log.Timber;


public class HomeFragment extends Fragment {

    private HomePresenter homePresenter;
    private HomeViewImpl homeView;
    private boolean firstLaunch = Prefs.getBoolean(Config.PREF_FIRST_BOOT, true);
    private boolean informationDismissed = Prefs.getBoolean(Config.PREF_INFORMATION_CARD_DISMISSED, false);

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
        homePresenter.retrieveCachedUser();
        checkFirstBoot();
        checkInformationCard();
        return homeView.getRootView();
    }

    private void checkInformationCard() {
        if (!informationDismissed){
            homeView.setInformationCardVisible(true);
        }
    }

    private void checkFirstBoot() {
        Timber.v("FirstLaunch - running first boot hints." + firstLaunch);
        Prefs.putBoolean(Config.PREF_FIRST_BOOT, false);
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
