package me.calebjones.pubgtrackerforandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.mikepenz.materialdrawer.Drawer;

import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.common.BaseActivity;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.ui.history.HistoryFragment;
import me.calebjones.pubgtrackerforandroid.ui.intro.IntroActivity;
import me.calebjones.pubgtrackerforandroid.ui.main.adapters.MainViewPagerAdapter;
import me.calebjones.pubgtrackerforandroid.ui.overview.OverviewFragment;
import me.calebjones.pubgtrackerforandroid.ui.statistics.StatsFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainContract.NavigatorProvider {

    private MainPresenter mainPresenter;
    private MainViewImpl mainView;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_APP_TOUR)) {
            startActivity(new Intent(this, IntroActivity.class));
        }
        mainView = new MainViewImpl(this, null);
        setContentView(mainView.getRootView());

        mainPresenter = new MainPresenter(mainView);
        mainPresenter.setNavigator(getNavigator(mainPresenter));
        mainView.navigation.selectTab(1);
        setupViewPager();
        mainView.setUpDrawer(this, savedInstanceState);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_USERNAME_HINT)) {
            mainView.showUserHint(this);
        }
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
        mainView.viewPager.setOffscreenPageLimit(3);
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

    @Override
    public void onStart() {
        super.onStart();
        Timber.v("onStart");
        mainPresenter.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.v("onResume");
        mainPresenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.v("onStop");
        mainPresenter.onStop();
    }

}
