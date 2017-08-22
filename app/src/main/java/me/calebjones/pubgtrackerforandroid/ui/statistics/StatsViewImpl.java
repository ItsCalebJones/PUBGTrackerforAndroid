package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;


public class StatsViewImpl implements StatsContract.View {

    private StatsContract.Presenter statsPresenter;
    private View mRootView;
    private Context context;

    public StatsViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
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
    public void setPresenter(StatsContract.Presenter presenter) {
        statsPresenter = presenter;
    }
}
