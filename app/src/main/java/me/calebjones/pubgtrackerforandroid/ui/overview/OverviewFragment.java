package me.calebjones.pubgtrackerforandroid.ui.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.common.BaseFragment;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.ui.intro.IntroActivity;
import timber.log.Timber;


public class OverviewFragment extends BaseFragment implements OverviewContract.NavigatorProvider {

    private OverviewPresenter overviewPresenter;
    private OverviewViewImpl overviewView;

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        overviewView = new OverviewViewImpl(getContext(), inflater, container);
        overviewPresenter = new OverviewPresenter(overviewView);
        overviewPresenter.setNavigator(getNavigator(overviewPresenter));
        overviewPresenter.registerEventBus();
        overviewPresenter.retrieveCachedUser();
        checkInformationCard();
        checkFirstBoot();
        return overviewView.getRootView();
    }

    private void checkInformationCard() {
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_WELCOME_CARD)){
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

    @NonNull
    @Override
    public OverviewContract.Navigator getNavigator(OverviewContract.Presenter presenter) {
        return new OverviewContract.Navigator() {
            @Override
            public void showIntro() {
                getActivity().startActivity(new Intent(getActivity(), IntroActivity.class));
            }
        };
    }
}
