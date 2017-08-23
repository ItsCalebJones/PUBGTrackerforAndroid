package me.calebjones.pubgtrackerforandroid.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.calebjones.pubgtrackerforandroid.common.BaseFragment;
import timber.log.Timber;


public class HistoryFragment extends BaseFragment {

    private HistoryPresenter historyPresenter;
    private HistoryViewImpl historyView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        historyView = new HistoryViewImpl(getContext(), inflater, container);
        historyPresenter = new HistoryPresenter(historyView);

        return historyView.getRootView();
    }

}
