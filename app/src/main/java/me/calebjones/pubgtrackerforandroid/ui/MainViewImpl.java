package me.calebjones.pubgtrackerforandroid.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchView;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;


public class MainViewImpl implements MainContract.View, SearchView.OnQueryTextListener,
        SearchView.OnMenuClickListener,
        OnBottomNavigationItemClickListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.contentFrame)
    ViewPager viewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private View mRootView;
    private MainContract.Presenter mainPresenter;
    private SearchHistoryTable historyDatabase;
    private Context context;
    private int[] image;
    public int[] color;
    public int[] topColor;
    private int currentColor;
    private int currentTopColor;

    public MainViewImpl(Context context, ViewGroup container) {
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_home, container);
        ButterKnife.bind(this, mRootView);
        setUpColors();
        setUpSearchView();
        setupNavigationView();
        navigation.setOnBottomNavigationItemClickListener(this);
    }

    private void setUpSearchView() {
        historyDatabase = new SearchHistoryTable(context);
        searchView.setOnQueryTextListener(this);
        searchView.setOnMenuClickListener(this);
        searchView.setHint(context.getResources().getString(R.string.hint));
    }

    private void setUpColors() {
        image = new int[] {
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_home_black_24dp,
                R.drawable.ic_history_black_24dp
        };
        color = new int[] {
                ContextCompat.getColor(context, R.color.material_color_red_500),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.material_color_blue_500)
        };

        topColor = new int[] {
                ContextCompat.getColor(context, R.color.material_color_red_700),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.material_color_blue_700)
        };

        currentColor = color[1];
        currentTopColor = topColor[1];
    }

    private void setupNavigationView() {

        BottomNavigationItem statsItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_statistics),
                        color[0],
                        image[0]);
        BottomNavigationItem homeItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_home),
                        color[1],
                        image[1]);
        BottomNavigationItem historyItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_history),
                        color[2],
                        image[2]);
        navigation.addTab(statsItem);
        navigation.addTab(homeItem);
        navigation.addTab(historyItem);
    }

    @Override
    public void onNavigationItemClick(int item) {
        switch (item) {
            case 1:
                onHomeClicked();
                updateTopColor(color[1], topColor[1]);
                break;
            case 2:
                onMatchHistoryClicked();
                updateTopColor(color[2], topColor[2]);
                break;
            case 0:
                onStatisticsClicked();
                updateTopColor(color[0], topColor[0]);
                break;
        }
    }

    private void updateTopColor(int color, int topColor) {
        final Window window = ((Activity) context).getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup viewGroup = coordinator;
        TransitionManager.beginDelayedTransition(viewGroup, new Recolor());
        appbar.setBackgroundColor(color);
        window.setStatusBarColor(topColor);
    }

    @Override
    public void onMenuClick() {
        searchView.open(true);
    }

    @Override
    public boolean onQueryTextSubmit(String string) {
        mainPresenter.searchQuerySubmitted(string);
        searchView.close(true);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String string) {
        return false;
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
    public void setPresenter(MainContract.Presenter presenter) {
        mainPresenter = presenter;
    }



    @Override
    public void setSupportToolbar(String title) {
    }

    @Override
    public void onHomeClicked() {
        mainPresenter.goHomeClicked();
    }

    @Override
    public void onMatchHistoryClicked() {
        mainPresenter.goMatchHistoryClicked();
    }

    @Override
    public void onStatisticsClicked() {
        mainPresenter.goStatisticsClicked();
    }

    @Override
    public void onPageChaged(int position) {
        switch (position) {
            case 0:
                navigation.selectTab(0);
                updateTopColor(color[0], topColor[0]);
                break;
            case 1:
                navigation.selectTab(1);
                updateTopColor(color[1], topColor[1]);
                break;
            case 2:
                navigation.selectTab(2);
                updateTopColor(color[2], topColor[2]);
                break;
        }
    }

    @Override
    public void closeSearchView() {
        searchView.close(true);
    }

    @Override
    public void openSearchView() {
        searchView.open(true);
    }

    @Override
    public void createSnackbar(String message) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show();
    }
}
