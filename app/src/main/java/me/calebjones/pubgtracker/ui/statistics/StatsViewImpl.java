package me.calebjones.pubgtracker.ui.statistics;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtracker.ui.views.ExtendedStatefulLayout;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.enums.PUBGSeason;
import me.calebjones.pubgtracker.data.models.PlayerStat;
import me.calebjones.pubgtracker.ui.views.PlaylistView;


public class StatsViewImpl implements StatsContract.View {

    @BindView(R.id.solo_playlist)
    PlaylistView soloPlaylist;
    @BindView(R.id.duo_playlist)
    PlaylistView duoPlaylist;
    @BindView(R.id.squad_playlist)
    PlaylistView squadPlaylist;
    @BindView(R.id.stateful_view)
    ExtendedStatefulLayout statefulView;
    @BindView(R.id.container)
    CoordinatorLayout container;
    @BindView(R.id.season_picker)
    AppCompatSpinner seasonPicker;
    @BindView(R.id.region_picker)
    AppCompatSpinner regionPicker;
    @BindView(R.id.history_sort_reset)
    AppCompatButton historySortReset;
    @BindView(R.id.history_sort_submit)
    AppCompatButton historySortSubmit;
    @BindView(R.id.sorting_view)
    LinearLayout sortingView;
    @BindView(R.id.sort_fab)
    FloatingActionButton sortFab;
    @BindView(R.id.viewMode_picker)
    AppCompatSpinner viewModePicker;
    @BindView(R.id.root)
    FrameLayout root;
    private StatsContract.Presenter statsPresenter;
    private View mRootView;
    private Context context;
    private ArrayAdapter<String> seasonAdapter;
    private ArrayAdapter<String> regionAdapter;
    private ArrayAdapter<String> viewModeAdapter;
    private Fragment fragment;
    private boolean sortViewVisible = false;

    public StatsViewImpl(Context context, LayoutInflater inflater, ViewGroup container, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        mRootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, mRootView);
        setUpSpinners();
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

    @Override
    public void setViewStateEmpty() {

    }

    @Override
    public void createSnackbar(String localizedMessage) {

    }

    @Override
    public void configureSoloPlaylist(PlayerStat stat) {
        soloPlaylist.setStats(stat);
    }

    @Override
    public void configureDuoPlaylist(PlayerStat stat) {
        duoPlaylist.setStats(stat);
    }

    @Override
    public void configureSquadPlaylist(PlayerStat stat) {
        squadPlaylist.setStats(stat);
    }

    @Override
    public void showEmpty() {
        statefulView.showEmpty();
        sortFab.show(true);
    }

    @Override
    public void showContent() {
        statefulView.showContent();
        sortFab.show(true);
    }

    @Override
    public void showNoUser() {
        statefulView.showNoUser();
        sortFab.hide(true);
    }

    @Override
    public int getRegionFilter() {
        return regionPicker.getSelectedItemPosition();
    }

    @Override
    public int getSeasonFilter() {
        return seasonPicker.getSelectedItemPosition();
    }

    @Override
    public int getViewModeFilter() {
        return viewModePicker.getSelectedItemPosition();
    }

    @Override
    public String getSeason(int position) {
        if (seasonAdapter.getCount() < position) {
            return null;
        }
        return seasonAdapter.getItem(position);
    }

    @Override
    public String getRegion(int position) {
        if (regionAdapter.getCount() < position) {
            return null;
        }
        return regionAdapter.getItem(position);
    }

    @Override
    public int getViewMode(int position) {
        return position;
    }

    @Override
    public void resetFilters() {
        seasonPicker.setSelection(0);
        regionPicker.setSelection(0);
    }

    @Override
    public void squadVisibility(int visibilityState) {
        TransitionManager.beginDelayedTransition(container);
        squadPlaylist.setVisibility(visibilityState);
    }

    @Override
    public void duoVisibility(int visibilityState) {
        TransitionManager.beginDelayedTransition(container);
        duoPlaylist.setVisibility(visibilityState);
    }

    @Override
    public void soloVisibility(int visibilityState) {
        TransitionManager.beginDelayedTransition(container);
        soloPlaylist.setVisibility(visibilityState);
    }

    private void setUpSpinners() {
        List<PUBGRegion> regionList = Arrays.asList(PUBGRegion.values());
        List<PUBGSeason> seasonList = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        seasonList.add(PUBGSeason.PRE6_2017);
        seasonList.add(PUBGSeason.PRE5_2017);
        seasonList.add(PUBGSeason.PRE4_2017);
        seasonList.add(PUBGSeason.PRE3_2017);
        seasonList.add(PUBGSeason.PRE2_2017);
        seasonList.add(PUBGSeason.PRE1_2017);

        modeList.add("First Person Perspective");
        modeList.add("Third Person Perspective");

        int index = 0;
        String[] regionArray = new String[regionList.size()];
        index = 0;
        for (PUBGRegion value : regionList) {
            regionArray[index] = value.getRegionName();
            index++;
        }

        index = 0;
        String[] seasonArray = new String[seasonList.size()];
        for (PUBGSeason value : seasonList) {
            seasonArray[index] = value.getSeasonName();
            index++;
        }

        seasonAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, seasonArray);

        regionAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, regionArray);

        viewModeAdapter = new ArrayAdapter<>(this.context,
                android.R.layout.simple_dropdown_item_1line, modeList);


        seasonPicker.setAdapter(seasonAdapter);

        regionPicker.setAdapter(regionAdapter);

        viewModePicker.setAdapter(viewModeAdapter);
    }

    @OnClick(R.id.sort_fab)
    public void onViewClicked() {
        checkSort();
    }

    private void checkSort() {
        TransitionManager.beginDelayedTransition(container);
        sortViewVisible = !sortViewVisible;
        sortingView.setVisibility(sortViewVisible ? View.VISIBLE : View.GONE);
        checkFabIcon();
    }

    private void checkFabIcon() {
        if (sortViewVisible) {
            sortFab.setImageDrawable(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_close)
                    .color(ContextCompat.getColor(context, R.color.material_color_white)));
        } else {
            sortFab.setImageDrawable(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_filter_list)
                    .color(ContextCompat.getColor(context, R.color.material_color_white)));
        }
    }

    @OnClick(R.id.history_sort_reset)
    public void onSortResetClicked() {
        checkSort();
        statsPresenter.resetClicked();
    }

    @OnClick(R.id.history_sort_submit)
    public void onSortSubmitClicked() {
        checkSort();
        statsPresenter.sortSubmitClicked();
    }

}
