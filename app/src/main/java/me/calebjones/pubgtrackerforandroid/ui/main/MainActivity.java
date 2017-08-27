package me.calebjones.pubgtrackerforandroid.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.common.BaseActivity;
import me.calebjones.pubgtrackerforandroid.ui.history.HistoryFragment;
import me.calebjones.pubgtrackerforandroid.ui.main.adapters.MainViewPagerAdapter;
import me.calebjones.pubgtrackerforandroid.ui.home.HomeFragment;
import me.calebjones.pubgtrackerforandroid.ui.fragments.TestFragment;
import me.calebjones.pubgtrackerforandroid.ui.statistics.StatsFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.NavigatorProvider {

    private MainPresenter mainPresenter;
    private MainViewImpl mainView;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = new MainViewImpl(this, null);
        setContentView(mainView.getRootView());

        mainPresenter = new MainPresenter(mainView);
        mainPresenter.setNavigator(getNavigator(mainPresenter));
        mainView.navigation.selectTab(1);
        setupViewPager();
        setupRecyclerView(savedInstanceState);
    }

    private void setupRecyclerView(Bundle savedInstanceState) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(new ImageHolder("http://res.cloudinary.com/dnkkbfy3m/image/upload/v1462465326/navbar_one_sqfhes.png"))
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home")
                                .withIcon(GoogleMaterial.Icon.gmd_home)
                                .withIdentifier(R.id.menu_home)
                                .withSelectable(true),
                        new PrimaryDrawerItem().withName("Map")
                                .withIcon(GoogleMaterial.Icon.gmd_home)
                                .withIdentifier(R.id.menu_map)
                                .withSelectable(true),
                        new PrimaryDrawerItem().withName("Compare")
                                .withIcon(GoogleMaterial.Icon.gmd_home)
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
        if (true){
            result.addStickyFooterItem(
                    new PrimaryDrawerItem().withName("Become a Supporter")
                            .withDescription("Get Pro Features")
                            .withIcon(GoogleMaterial.Icon.gmd_mood)
                            .withIdentifier(R.id.menu_support)
                            .withSelectable(false));
        }
    }


    private MainViewPagerAdapter setupViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        StatsFragment statFragment = new StatsFragment();
        adapter.addFragment(statFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(historyFragment);
        mainView.viewPager.setAdapter(adapter);
        mainView.viewPager.setCurrentItem(1);
        mainView.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mainView.onPageChaged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return adapter;
    }

    @NonNull
    @Override
    public MainContract.Navigator getNavigator(MainContract.Presenter presenter) {
        return new MainNavigator(this, mainView.viewPager);
    }


}
