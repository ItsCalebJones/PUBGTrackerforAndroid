package me.calebjones.pubgtrackerforandroid.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.ui.home.HomeContract;

/**
 * Created by Caleb on 8/20/2017.
 */

public class HistoryViewImpl implements HistoryContract.View {

    private HistoryContract.Presenter historyPresenter;
    private View mRootView;
    private Context context;

    public HistoryViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, mRootView);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        historyPresenter = presenter;
    }
}
