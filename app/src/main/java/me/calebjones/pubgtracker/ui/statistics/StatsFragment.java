package me.calebjones.pubgtracker.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.calebjones.pubgtracker.common.BaseFragment;
import timber.log.Timber;


public class StatsFragment extends BaseFragment {

    private StatsPresenter statsPresenter;
    private StatsViewImpl statsView;
    private boolean visibleToUser = false;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        statsView = new StatsViewImpl(getContext(), inflater, container, this);
        statsPresenter = new StatsPresenter(statsView);
        statsPresenter.retrieveCachedUser();
        return statsView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.v("onStart");
        statsPresenter.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.v("onResume");
        statsPresenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.v("onStop");
        statsPresenter.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Timber.v("setUserVisibleHint", isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        this.visibleToUser = isVisibleToUser;
    }
}
