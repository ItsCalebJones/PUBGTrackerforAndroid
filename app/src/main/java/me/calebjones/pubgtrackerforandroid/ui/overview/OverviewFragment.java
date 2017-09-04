package me.calebjones.pubgtrackerforandroid.ui.overview;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.easyprefs.library.Prefs;

import me.calebjones.pubgtrackerforandroid.common.BaseFragment;
import me.calebjones.pubgtrackerforandroid.data.Config;
import timber.log.Timber;


public class OverviewFragment extends BaseFragment {

    private OverviewPresenter overviewPresenter;
    private OverviewViewImpl overviewView;
    private boolean informationDismissed = Prefs.getBoolean(Config.PREF_INFORMATION_CARD_DISMISSED, false);

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        overviewView = new OverviewViewImpl(getContext(), inflater, container);
        overviewPresenter = new OverviewPresenter(overviewView);
        overviewPresenter.registerEventBus();
        overviewPresenter.retrieveCachedUser();
        checkFirstBoot();
        checkInformationCard();
        return overviewView.getRootView();
    }

    private void checkInformationCard() {
        if (!informationDismissed){
            overviewView.setInformationCardVisible(true);
        }
    }

    private void checkFirstBoot() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.v("onStart");
        overviewPresenter.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.v("onResume");
        overviewPresenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.v("onStop");
        overviewPresenter.onStop();
    }
}
