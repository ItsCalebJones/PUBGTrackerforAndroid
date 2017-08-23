package me.calebjones.pubgtrackerforandroid.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.transitionseverywhere.TransitionManager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.kinst.jakub.view.SimpleStatefulLayout;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGMode;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGRegion;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;


public class HistoryViewImpl implements HistoryContract.View {

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;
    @BindView(R.id.stateful_view)
    SimpleStatefulLayout statefulView;
    @BindView(R.id.season_picker)
    Spinner seasonPicker;
    @BindView(R.id.mode_picker)
    Spinner modePicker;
    @BindView(R.id.region_picker)
    Spinner regionPicker;
    @BindView(R.id.sorting_view)
    LinearLayout sortingView;
    @BindView(R.id.sort_fab)
    FloatingActionButton sortFab;
    @BindView(R.id.container)
    CoordinatorLayout container;
    @BindView(R.id.history_sort_submit)
    AppCompatButton historySortSubmit;
    private HistoryContract.Presenter historyPresenter;
    private View mRootView;
    private Context context;
    private boolean sortViewVisible = false;
    private ArrayAdapter<String> seasonAdapter;
    private ArrayAdapter<String> regionAdapter;
    private ArrayAdapter<String> modeAdapter;

    public HistoryViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, mRootView);
        statefulView.setEmptyImageDrawable(new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_info_outline)
                .color(ContextCompat.getColor(context, R.color.material_color_white))
                .sizeDp(64));

        setUpSpinners();
    }

    private void setUpSpinners() {
        List<PUBGMode> modeList = Arrays.asList(PUBGMode.values());
        List<PUBGRegion> regionList = Arrays.asList(PUBGRegion.values());
        List<PUBGSeason> seasonList = Arrays.asList(PUBGSeason.values());

        String[] modeArray = new String[modeList.size()];
        int index = 0;
        for (PUBGMode value : modeList) {
            modeArray[index] = value.getModeName();
            index++;
        }

        String[] regionArray = new String[regionList.size()];
        index = 0;
        for (PUBGRegion value : regionList) {
            regionArray[index] = value.getRegionName();
            index++;
        }

        String[] seasonArray = new String[seasonList.size()];
        index = 0;
        for (PUBGSeason value : seasonList) {
            seasonArray[index] = value.getSeasonKey();
            index++;
        }
        modeAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, modeArray);

        seasonAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, seasonArray);

        regionAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, regionArray);

        modePicker.setAdapter(modeAdapter);

        seasonPicker.setAdapter(seasonAdapter);

        regionPicker.setAdapter(regionAdapter);
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

    @OnClick(R.id.sort_fab)
    public void onFabViewClicked() {
        TransitionManager.beginDelayedTransition(container);
        sortViewVisible = !sortViewVisible;
        sortingView.setVisibility(sortViewVisible ? View.VISIBLE : View.GONE);
        checkFabIcon();
    }

    @OnClick(R.id.history_sort_submit)
    public void onSortSubmitClicked(){
        sortViewVisible = !sortViewVisible;
        sortingView.setVisibility(sortViewVisible ? View.VISIBLE : View.GONE);
        checkFabIcon();
    }

    private void checkFabIcon() {
        if (sortViewVisible){
            sortFab.setImageDrawable(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_close)
                    .color(ContextCompat.getColor(context, R.color.material_color_white)));
        } else {
            sortFab.setImageDrawable(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_filter_list)
                    .color(ContextCompat.getColor(context, R.color.material_color_white)));
        }
    }

    @Override
    public void setViewState(SimpleStatefulLayout.State state) {
        statefulView.setSt
    }

    @Override
    public void setRefreshEnabled(boolean state) {

    }

    @Override
    public void createSnackbar(String localizedMessage) {

    }

    @Override
    public void setRefreshing(boolean state) {

    }
}
