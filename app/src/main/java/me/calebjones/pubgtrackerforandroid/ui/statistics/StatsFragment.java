package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.calebjones.pubgtrackerforandroid.common.BaseFragment;
import timber.log.Timber;


public class StatsFragment extends BaseFragment {

    private StatsPresenter statsPresenter;
    private StatsViewImpl statsView;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        statsView = new StatsViewImpl(getContext(), inflater, container);
        statsPresenter = new StatsPresenter(statsView);
        return statsView.getRootView();
    }

}
