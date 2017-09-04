package me.calebjones.pubgtrackerforandroid.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;


public class MainViewImpl implements MainContract.View, SearchView.OnQueryTextListener,
        SearchView.OnMenuClickListener,
        AHBottomNavigation.OnTabSelectedListener {

    public int[] color;
    public int[] topColor;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.navigation_view)
    AHBottomNavigation navigation;
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
    private Drawer result;
    private AccountHeader accountHeader;

    public MainViewImpl(Context context, ViewGroup container) {
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_home, container);
        ButterKnife.bind(this, mRootView);
        setUpColors();
        setUpSearchView();
        setupNavigationView();
        navigation.setOnTabSelectedListener(this);
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
        searchView.setVoice(false);
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

        AHBottomNavigationItem statsItem = new AHBottomNavigationItem
                (context.getResources().getString(R.string.title_statistics),
                        R.drawable.ic_dashboard_black_24dp,
                        color[0]);

        AHBottomNavigationItem homeItem = new AHBottomNavigationItem
                (context.getResources().getString(R.string.title_home),
                        R.drawable.ic_account_circle_black_24,
                        color[1]);

        AHBottomNavigationItem historyItem = new AHBottomNavigationItem
                (context.getResources().getString(R.string.title_history),
                        R.drawable.ic_history_black_24dp,
                        color[2]);

        navigation.addItem(statsItem);
        navigation.addItem(homeItem);
        navigation.addItem(historyItem);
        navigation.setTranslucentNavigationEnabled(true);
        navigation.setColored(true);
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
    public void onOverviewClicked() {
        mainPresenter.goOverviewClicked();
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
                navigation.setCurrentItem(0);
                updateTopColor(color[0], topColor[0]);
                break;
            case 1:
                navigation.setCurrentItem(1);
                updateTopColor(color[1], topColor[1]);
                break;
            case 2:
                navigation.setCurrentItem(2);
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
    public void createSnackbarSetCurrentUser(String message, final User user) {
        Snackbar snackbar = Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG);

        snackbar.setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.setUserAsCurrent(user);
            }
        });
        snackbar.show();
    }

    @Override
    public void setUpDrawer(Activity activity, Bundle savedInstanceState) {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(false)
                .withHeaderBackground(new ImageHolder("http://i.imgur.com/Oe5nnI9.jpg"))
                .withAccountHeader(R.layout.material_drawer_header_custom)
                .withSavedInstance(savedInstanceState)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        if (!currentProfile && profile.getIdentifier() != R.id.menu_profile_settings) {
                            mainPresenter.setCurrentUser(profile.getIdentifier());
                        }
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(true)
                .withHasStableIds(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("Overview")
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_account_circle)
                                .withIdentifier(R.id.menu_overview),
                        new PrimaryDrawerItem()
                                .withName("Statistics")
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_dashboard)
                                .withIdentifier(R.id.menu_statistics),
                        new PrimaryDrawerItem()
                                .withName("Sessions")
                                .withLevel(2)
                                .withIcon(GoogleMaterial.Icon.gmd_history)
                                .withIdentifier(R.id.menu_history),
//                        new ExpandableDrawerItem()
//                                .withName("Home")
//                                .withIcon(GoogleMaterial.Icon.gmd_home)
//                                .withIdentifier(R.id.menu_home_master)
//                                .withSelectable(false)
//                                .withSubItems(
//                                        new SecondaryDrawerItem()
//                                                .withName("Statistics")
//                                                .withLevel(2)
//                                                .withIcon(GoogleMaterial.Icon.gmd_dashboard)
//                                                .withIdentifier(R.id.menu_statistics),
//                                        new SecondaryDrawerItem()
//                                                .withName("Overview")
//                                                .withLevel(2)
//                                                .withIcon(GoogleMaterial.Icon.gmd_account_circle)
//                                                .withIdentifier(R.id.menu_overview),
//                                        new SecondaryDrawerItem()
//                                                .withName("Sessions")
//                                                .withLevel(2)
//                                                .withIcon(GoogleMaterial.Icon.gmd_history)
//                                                .withIdentifier(R.id.menu_history)
//
//                                ),
//                        new PrimaryDrawerItem().withName("Map")
//                                .withIcon(GoogleMaterial.Icon.gmd_map)
//                                .withIdentifier(R.id.menu_map)
//                                .withSelectable(false),
//                        new PrimaryDrawerItem().withName("Compare")
//                                .withIcon(GoogleMaterial.Icon.gmd_compare)
//                                .withIdentifier(R.id.menu_compare)
//                                .withSelectable(false),
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
                                .withIcon(R.drawable.logo)
                                .withName("PUBG Tracker")
                                .withIdentifier(R.id.menu_tracker)
                                .withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch ((int) drawerItem.getIdentifier()) {
                                case R.id.menu_home_master:
                                    break;
                                case R.id.menu_overview:
                                    mainPresenter.goOverviewClicked();
                                    break;
                                case R.id.menu_history:
                                    mainPresenter.goMatchHistoryClicked();
                                    break;
                                case R.id.menu_statistics:
                                    mainPresenter.goStatisticsClicked();
                                    break;
                                case R.id.menu_map:
                                    mainPresenter.goMapClicked();
                                    break;
                                case R.id.menu_compare:
                                    mainPresenter.goCompareClicked();
                                    break;
                                case R.id.menu_new:
                                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                                            .title("Whats New?")
                                            .content("NOTHING - It's a Beta!")
                                            .positiveText("Got it!");
                                    builder.show();
                                    break;
                                case R.id.menu_feedback:
                                    createSnackbar("Feedback mechanism coming!");
                                    break;
                                case R.id.menu_tracker:
                                    String url = "https://pubgtracker.com";
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    context.startActivity(i);
                                    break;
                                case R.id.menu_settings:
                                    mainPresenter.goToSettings();
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();

//        if (!SupporterHelper.isSupporter()){
        if (true) {
            result.addStickyFooterItem(
                    new PrimaryDrawerItem().withName("Settings")
                            .withIcon(GoogleMaterial.Icon.gmd_settings)
                            .withIdentifier(R.id.menu_settings)
                            .withSelectable(false));
//                    new PrimaryDrawerItem().withName("Become a Supporter")
//                            .withDescription("Get Pro Features")
//                            .withIcon(GoogleMaterial.Icon.gmd_mood)
//                            .withIdentifier(R.id.menu_support)
//                            .withSelectable(false));
        }
        mainPresenter.getUsers();
        mainPresenter.setCurrentUser();
    }

    @Override
    public void addDrawerUser(User user) {
        accountHeader.addProfiles(convertUserToProfile(user));
    }

    @Override
    public void setActiveUser(User user) {
        if (user == null) {
            Timber.e("User cannot be null, no active users found.");
            return;
        }
//        accountHeader.setHeaderBackground(new ImageHolder("http://res.cloudinary.com/dnkkbfy3m/image/upload/e_blur:1500/v1503931147/orR8Jtd_ntupcz.jpg"));
        accountHeader.setActiveProfile(convertUserToProfile(user));
    }

    @Override
    public void setUsers(List<User> users) {
//        if (users.size() > 0){
//            accountHeader.setHeaderBackground(new ImageHolder("http://res.cloudinary.com/dnkkbfy3m/image/upload/e_blur:1500/v1503931147/orR8Jtd_ntupcz.jpg"));
//        }
        for (User user : users) {
            IProfile profile = convertUserToProfile(user);
            accountHeader.addProfiles(profile);
        }
    }

    @Override
    public void deleteUser(User user) {
        for (IProfile profile : accountHeader.getProfiles()) {
            profile.getName();
            user.getPubgTrackerId();
            if (profile.getIdentifier() == user.getPubgTrackerId()) {
                accountHeader.removeProfile(profile);
                if (user.isCurrentUser()) {
                    accountHeader.setActiveProfile(convertUserToProfile(user));
                }
            }
        }
    }

    @Override
    public void showUserHint(Activity activity) {
        new MaterialTapTargetPrompt.Builder(activity)
                .setTarget(searchView)
                .setFocalColour(Color.TRANSPARENT)
                .setPrimaryText("Search Your Username")
                .setSecondaryText("Tap the bar to find your username.")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_DISMISSING) {
                            Once.markDone(Config.SHOW_USERNAME_HINT);
                        }
                    }
                })
                .show();
    }

    private IProfile convertUserToProfile(User user) {
        IProfile profile = new ProfileDrawerItem()
                .withIdentifier(user.getPubgTrackerId())
                .withName(user.getPlayerName())
                .withIcon(user.getAvatar());
        return profile;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (navigation.isHidden()) {
            navigation.restoreBottomNavigation(true);
        }
        switch (position) {
            case 1:
                onOverviewClicked();
                updateTopColor(color[1], topColor[1]);
                return true;
            case 2:
                onMatchHistoryClicked();
                updateTopColor(color[2], topColor[2]);
                return true;
            case 0:
                onStatisticsClicked();
                updateTopColor(color[0], topColor[0]);
                return true;
            default:
                return false;
        }
    }
}
