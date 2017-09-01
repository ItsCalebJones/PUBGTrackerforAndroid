package me.calebjones.pubgtrackerforandroid.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.transitionseverywhere.TransitionManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.kinst.jakub.view.SimpleStatefulLayout;
import io.realm.RealmResults;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGMode;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGRegion;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.ui.views.ExtendedStatefulLayout;
import timber.log.Timber;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;


public class HistoryViewImpl implements HistoryContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;
    @BindView(R.id.stateful_view)
    ExtendedStatefulLayout statefulView;
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
    @BindView(R.id.history_sort_reset)
    AppCompatButton historySortReset;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshView;

    private HistoryContract.Presenter historyPresenter;
    private View mRootView;
    private Context context;
    private boolean sortViewVisible = false;
    private ArrayAdapter<String> seasonAdapter;
    private ArrayAdapter<String> regionAdapter;
    private ArrayAdapter<String> modeAdapter;
    private HistoryRecyclerAdapter historyAdapter;
    private Fragment fragment;

    public HistoryViewImpl(Context context, LayoutInflater inflater, ViewGroup container, Fragment fragment) {
        Timber.v("HistoryViewImpl  - Creating view...");
        this.context = context;
        this.fragment = fragment;
        Timber.v("HistoryViewImpl  - Inflating layout...");
        mRootView = inflater.inflate(R.layout.fragment_history, container, false);
        Timber.v("HistoryViewImpl  - Binding View... with rootview.");
        ButterKnife.bind(this, mRootView);
        Timber.v("HistoryViewImpl  - Creating Stateful layout.");
        statefulView.setEmptyImageDrawable(new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_info_outline)
                .color(ContextCompat.getColor(context, R.color.material_color_white))
                .sizeDp(64));
        Timber.v("HistoryViewImpl  - Setting up Spinners");
        setUpSpinners();
        Timber.v("HistoryViewImpl  - Configuring Recyler");
        setUpRecyclerView();
        refreshView.setOnRefreshListener(this);
    }

    private void setUpRecyclerView() {
        historyAdapter = new HistoryRecyclerAdapter(context);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        historyRecyclerView.setNestedScrollingEnabled(false);
        historyRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration
                        .Builder(context)
                        .build());
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
            seasonArray[index] = value.getSeasonName();
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
    public String getMode(int position) {
        if (modeAdapter.getCount() < position) {
            return null;
        }
        return modeAdapter.getItem(position);
    }

    @Override
    public void resetFilters() {
        modePicker.setSelection(0);
        seasonPicker.setSelection(0);
        regionPicker.setSelection(0);
    }

    @Override
    public void showFilterHint() {
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_FILTER_HINT)) {
            new MaterialTapTargetPrompt.Builder(fragment.getActivity())
                    .setTarget(sortFab)
                    .setPrimaryText("Filter Results")
                    .setSecondaryText("Tap the button to filter.")
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING) {
                                Once.markDone(Config.SHOW_FILTER_HINT);
                            }
                        }
                    })
                    .show();
        }
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

    @Override
    public void showEmpty() {
        statefulView.showEmpty();
        sortFab.show(true);
    }


    @Override
    public void showContent() {
        statefulView.setState(SimpleStatefulLayout.State.CONTENT);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_FILTER_HINT)) {

        }
        sortFab.show(true);
    }

    @Override
    public void showNoUser() {
        statefulView.showNoUser();
        sortFab.hide(true);
    }

    //TODO
    @Override
    public void setRefreshEnabled(boolean state) {
        refreshView.setEnabled(state);
    }

    //TODO
    @Override
    public void createSnackbar(String message) {

    }

    //TODO
    @Override
    public void setRefreshing(boolean state) {
        refreshView.setRefreshing(state);
    }

    @Override
    public void setAdapterMatches(RealmResults<Match> matches) {
        historyAdapter.setMatches(matches);
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
    public int getModeFilter() {
        return modePicker.getSelectedItemPosition();
    }

    @OnClick(R.id.sort_fab)
    public void onFabViewClicked() {
        checkSort();
    }

    @OnClick(R.id.history_sort_submit)
    public void onSortSubmitClicked() {
        checkSort();
        historyPresenter.sortSubmitClicked();
    }

    @OnClick(R.id.history_sort_reset)
    public void onResetClicked() {
        checkSort();
        historyPresenter.resetClicked();
    }

    private void checkSort() {
        TransitionManager.beginDelayedTransition(container);
        sortViewVisible = !sortViewVisible;
        sortingView.setVisibility(sortViewVisible ? View.VISIBLE : View.GONE);
        checkFabIcon();
    }

    @Override
    public void onRefresh() {
        historyPresenter.refreshCurrentUser();
    }
}
