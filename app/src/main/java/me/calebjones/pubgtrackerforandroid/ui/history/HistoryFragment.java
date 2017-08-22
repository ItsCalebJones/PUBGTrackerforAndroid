package me.calebjones.pubgtrackerforandroid.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.calebjones.pubgtrackerforandroid.common.BaseFragment;
import me.calebjones.pubgtrackerforandroid.ui.home.HomePresenter;
import me.calebjones.pubgtrackerforandroid.ui.home.HomeViewImpl;
import timber.log.Timber;

/**
 * Created by Caleb on 8/20/2017.
 */

public class HistoryFragment extends BaseFragment {

    private HistoryPresenter historyPresenter;
    private HistoryViewImpl historyView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.v("onCreateView");
        historyView = new HistoryViewImpl(getContext(), inflater, container);
        historyPresenter = new HistoryPresenter(historyView);
        return historyView.getRootView();
    }

}
