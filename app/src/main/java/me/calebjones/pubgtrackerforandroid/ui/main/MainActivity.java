package me.calebjones.pubgtrackerforandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.mikepenz.materialdrawer.Drawer;

import me.calebjones.pubgtrackerforandroid.common.BaseActivity;
import me.calebjones.pubgtrackerforandroid.ui.history.HistoryFragment;
import me.calebjones.pubgtrackerforandroid.ui.main.adapters.MainViewPagerAdapter;
import me.calebjones.pubgtrackerforandroid.ui.overview.OverviewFragment;
import me.calebjones.pubgtrackerforandroid.ui.statistics.StatsFragment;

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
        mainView.setUpDrawer(this, savedInstanceState);
    }



    private MainViewPagerAdapter setupViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        OverviewFragment homeFragment = new OverviewFragment();
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
