package me.calebjones.pubgtrackerforandroid.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchHistoryTable;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.view.IconicsImageView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import timber.log.Timber;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;


public class MainViewImpl implements MainContract.View, SearchView.OnQueryTextListener,
        SearchView.OnMenuClickListener,
        AHBottomNavigation.OnTabSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    public int[] color;
    public int[] topColor;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.navigation_view)
    AHBottomNavigation navigation;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.contentFrame)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.favorite_icon)
    LottieAnimationView favoriteIcon;
    @BindView(R.id.current_rank)
    TextView currentRank;
    @BindView(R.id.current_KD)
    TextView currentKD;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private View mRootView;
    private MainContract.Presenter mainPresenter;
    private SearchHistoryTable historyDatabase;
    private Context context;
    private Drawer result;
    private AccountHeader accountHeader;
    private boolean favoriteUser;
    private Window window;

    public MainViewImpl(Context context, ViewGroup container, Window window) {
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_home, container);
        ButterKnife.bind(this, mRootView);
        setUpColors();
        setUpSearchView();
        setupNavigationView();
        refresh.setOnRefreshListener(this);
        this.window = window;
        ViewCompat.setElevation(appbar, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appbar.setElevation(0);
        }
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
                ContextCompat.getColor(context, R.color.colorAccentAlt),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent)
        };

        topColor = new int[]{
                ContextCompat.getColor(context, R.color.colorAccentAltDark),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.colorAccentDark)
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
        navigation.setColored(true);
        navigation.setBehaviorTranslationEnabled(true);
        navigation.setTranslucentNavigationEnabled(true);
        navigation.setOnTabSelectedListener(this);
    }

    private void updateTopColor(int color, int topColor) {
        ViewGroup viewGroup = coordinator;
        TransitionManager.beginDelayedTransition(viewGroup, new Recolor());
        appbar.setBackgroundColor(color);
        if (result != null) {
            result.getDrawerLayout().setStatusBarBackgroundColor(topColor);
            result.getHeader().setBackgroundColor(color);
        }
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(topColor);
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
        refresh.setRefreshing(true);
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
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.playerunknowns_battlegrounds_guy)
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
                .withTranslucentNavigationBar(true)
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
                                .withName("PUBG")
                                .withDescription("Tracker Network")
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
        Timber.d("setActiveUser - Configuring user %s", user.getPlayerName());
        accountHeader.setActiveProfile(convertUserToProfile(user));
        PlayerStat highestElo = null;
        for (PlayerStat playerStat : user.getPlayerStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    !Objects.equals(playerStat.getRegion(), "agg")) {
                float elo = playerStat.getStats().get(9).getValueDec();
                if (highestElo == null) {
                    highestElo = playerStat;
                } else if (highestElo.getStats().get(9).getValueDec() < elo) {
                    highestElo = playerStat;
                }
            }
        }
        setProfileName(user.getPlayerName());
        setFavoriteUserIcon(user.isFavoriteUser());
        if (highestElo != null) {
            setCurrentRatingAndRank(
                    highestElo.getStats().get(9).getValue(),
                    String.valueOf(highestElo.getStats().get(9).getRank()),
                    findKD(user));
        } else {
            setCurrentRatingAndRank("", " N/A", "");
        }
    }

    private String findKD(User user) {
        Float highestKD = null;
        for (PlayerStat playerStat : user.getPlayerStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    Objects.equals(playerStat.getRegion(), "agg")) {
                float currentKd = playerStat.getStats().get(0).getValueDec();
                if (highestKD == null) {
                    highestKD = currentKd;
                } else if (highestKD < currentKd) {
                    highestKD = currentKd;
                }
            }
        }
        if (highestKD != null) {
            return String.valueOf(highestKD);
        } else {
            return "Unknown";
        }
    }

    @OnClick(R.id.favorite_icon)
    public void onCurrentUserIconClicked() {
        favoriteUser = !favoriteUser;
        setFavoriteUserIcon(favoriteUser);
        mainPresenter.setFavoriteUserState(favoriteUser);
    }

    private void setFavoriteUserIcon(boolean state) {
        favoriteUser = state;
        if (favoriteUser) {
            favoriteIcon.playAnimation();
//            favoriteIcon.setIcon(new IconicsDrawable(context)
//                    .icon(GoogleMaterial.Icon.gmd_favorite)
//                    .color(ContextCompat.getColor(context, R.color.material_color_white))
//                    .sizeDp(28));
        } else {
            favoriteIcon.setProgress(0f);
//            favoriteIcon.setIcon(new IconicsDrawable(context)
//                    .icon(GoogleMaterial.Icon.gmd_favorite_border)
//                    .color(ContextCompat.getColor(context, R.color.material_color_white))
//                    .sizeDp(28));
        }
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

    @Override
    public void setRefreshing(boolean refreshing) {
        refresh.setRefreshing(refreshing);
    }

    @Override
    public void enableDisableSwipeRefresh(boolean enable) {
        if (refresh != null) {
            if (!refresh.isRefreshing()){
                refresh.setEnabled(enable);
            }
        }
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

    @Override
    public void setProfileName(String name) {
        profileName.setText(name);
    }

    @Override
    public void setCurrentRatingAndRank(String rating, String rank, String kd) {
        String stringRank = "#" + rank;
        String stringKd = context.getString(R.string.current_Kd) + " " + kd;
        currentRank.setText(rating);
        currentKD.setText(stringKd);
        currentKD.setVisibility(View.VISIBLE);
        currentRank.setVisibility(View.VISIBLE);
    }

    public void onPageScrolled(int position, float positionOffset) {
        if (positionOffset == 0) {
            return;
        }
        // Retrieve the current and next ColorFragment
        final int fromColor = color[position];
        final int toColor = color[position + 1];

        final int fromColorTop = topColor[position];
        final int toColorTop = topColor[position + 1];

        // Blend the colors and adjust the ActionBar
        final int blended = blendColors(toColor, fromColor, positionOffset);
        final int blendedTop = blendColors(toColorTop, fromColorTop, positionOffset);

        appbar.setBackgroundColor(blended);
        navigation.setBackgroundColor(blended);
//        if (result != null) {
//            result.getDrawerLayout().setStatusBarBackgroundColor(blendedTop);
//        }
//        // clear FLAG_TRANSLUCENT_STATUS flag:
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(blendedTop);
        }
    }

    private int blendColors(int from, int to, float ratio) {
        final float inverseRation = 1f - ratio;
        final float r = Color.red(from) * ratio + Color.red(to) * inverseRation;
        final float g = Color.green(from) * ratio + Color.green(to) * inverseRation;
        final float b = Color.blue(from) * ratio + Color.blue(to) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);
    }

    @Override
    public void onRefresh() {
        mainPresenter.refreshCurrentUser();
    }
}
