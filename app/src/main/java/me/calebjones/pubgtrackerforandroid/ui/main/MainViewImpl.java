package me.calebjones.pubgtrackerforandroid.ui.main;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;


public class MainViewImpl implements MainContract.View, SearchView.OnQueryTextListener,
        SearchView.OnMenuClickListener,
        OnBottomNavigationItemClickListener {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.navigation_view)
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
    public int[] color;
    public int[] topColor;
    private Drawer result;
    private AccountHeader accountHeader;
    private ProfileDrawerItem profileDrawerItem;

    public MainViewImpl(Context context, ViewGroup container) {
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_home, container);
        ButterKnife.bind(this, mRootView);
        setUpColors();
        setUpSearchView();
        setupNavigationView();
        navigation.setOnBottomNavigationItemClickListener(this);
        ViewCompat.setElevation(appbar, 0);
        appbar.setElevation(0);
    }

    private void setUpSearchView() {
        historyDatabase = new SearchHistoryTable(context);
        historyDatabase.setHistorySize(5);
        searchView.setOnQueryTextListener(this);
        searchView.setOnMenuClickListener(this);
        searchView.setHint(context.getResources().getString(R.string.hint));
        final SearchAdapter searchAdapter = new SearchAdapter(context, historyDatabase.getAllItems(1));
        searchAdapter.addOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SearchItem item = searchAdapter.getSuggestionsList().get(position);
                onQueryTextSubmit(String.valueOf(item.get_text()));
            }
        });
        searchView.setAdapter(searchAdapter);
        searchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
            @Override
            public void onMenuClick() {
                result.openDrawer();
            }
        });
    }

    private void setUpColors() {
        color = new int[]{
                ContextCompat.getColor(context, R.color.material_color_red_500),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.material_color_blue_500)
        };

        topColor = new int[]{
                ContextCompat.getColor(context, R.color.material_color_red_700),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.material_color_blue_700)
        };
    }

    private void setupNavigationView() {

        BottomNavigationItem statsItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_statistics),
                        color[0],
                        R.drawable.ic_dashboard_black_24dp);
        BottomNavigationItem homeItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_home),
                        color[1],
                        R.drawable.ic_home_black_24dp);
        BottomNavigationItem historyItem = new BottomNavigationItem
                (context.getResources().getString(R.string.title_history),
                        color[2],
                        new IconicsDrawable(context)
                                .icon(GoogleMaterial.Icon.gmd_account_circle));
        navigation.addTab(statsItem);
        navigation.addTab(homeItem);
        navigation.addTab(historyItem);
        navigation.disableViewPagerSlide();
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
        ViewGroup viewGroup = coordinator;
        TransitionManager.beginDelayedTransition(viewGroup, new Recolor());
        appbar.setBackgroundColor(color);
        if (result != null) {
            result.getDrawerLayout().setStatusBarBackgroundColor(topColor);
        }
    }

    @Override
    public void onMenuClick() {
        searchView.open(true);
    }

    @Override
    public boolean onQueryTextSubmit(String string) {
        historyDatabase.addItem(new SearchItem(string), 1);
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

    @Override
    public void createSnackbarSetDefaultUser(String message, final User user) {
        Snackbar snackbar = Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG);

        snackbar.setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.setUserAsDefault(user);
            }
        });
        snackbar.show();
    }

    @Override
    public void setUpDrawer(Activity activity, Bundle savedInstanceState) {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(false)
                .withHeaderBackground(new ImageHolder("http://i.imgur.com/orR8Jtd.jpg"))
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(true)
                .withHasStableIds(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new ExpandableDrawerItem()
                                .withName("Home")
                                .withIcon(GoogleMaterial.Icon.gmd_home)
                                .withIdentifier(R.id.menu_home)
                                .withSelectable(false)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName("Overview")
                                                .withLevel(2)
                                                .withIcon(GoogleMaterial.Icon.gmd_account_circle)
                                                .withIdentifier(2001),
                                        new SecondaryDrawerItem()
                                                .withName("History")
                                                .withLevel(2)
                                                .withIcon(GoogleMaterial.Icon.gmd_dashboard)
                                                .withIdentifier(2002),
                                        new SecondaryDrawerItem()
                                                .withName("Statistics")
                                                .withLevel(2)
                                                .withIcon(GoogleMaterial.Icon.gmd_history)
                                                .withIdentifier(2003)
                                ),
                        new PrimaryDrawerItem().withName("Map")
                                .withIcon(GoogleMaterial.Icon.gmd_map)
                                .withIdentifier(R.id.menu_map)
                                .withSelectable(true),
                        new PrimaryDrawerItem().withName("Compare")
                                .withIcon(GoogleMaterial.Icon.gmd_compare)
                                .withIdentifier(R.id.menu_compare)
                                .withSelectable(true),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withIcon(GoogleMaterial.Icon.gmd_info_outline)
                                .withName("What's New?")
                                .withDescription("See the changelog.")
                                .withIdentifier(R.id.menu_new)
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withIcon(GoogleMaterial.Icon.gmd_feedback)
                                .withName("Feedback")
                                .withDescription("Found a bug?")
                                .withIdentifier(R.id.menu_feedback)
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withIcon(FontAwesome.Icon.faw_twitter)
                                .withName("Twitter")
                                .withDescription("Stay Connected!")
                                .withIdentifier(R.id.menu_twitter)
                                .withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                        }
                        return false;
                    }
                }).build();

//        if (!SupporterHelper.isSupporter()){
        if (true) {
            result.addStickyFooterItem(
                    new PrimaryDrawerItem().withName("Become a Supporter")
                            .withDescription("Get Pro Features")
                            .withIcon(GoogleMaterial.Icon.gmd_mood)
                            .withIdentifier(R.id.menu_support)
                            .withSelectable(false));
        }
    }

    @Override
    public void setDrawerUser(User user) {
        if (profileDrawerItem != null){
            profileDrawerItem.withName(user.getPlayerName()).withIcon(user.getAvatar());
            accountHeader.updateProfile(profileDrawerItem);
        } else {
            profileDrawerItem = new ProfileDrawerItem()
                    .withName(user.getPlayerName())
                    .withIcon(user.getAvatar());
            accountHeader.addProfiles(profileDrawerItem);
        }
        accountHeader.setActiveProfile(profileDrawerItem);
    }
}
