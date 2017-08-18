package me.calebjones.pubgtrackerforandroid.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import me.calebjones.pubgtrackerforandroid.common.BaseActivity;
import me.calebjones.pubgtrackerforandroid.ui.adapters.ViewPagerAdapter;
import me.calebjones.pubgtrackerforandroid.ui.home.HomeFragment;
import me.calebjones.pubgtrackerforandroid.ui.fragments.TestFragment;

public class MainActivity extends BaseActivity implements MainContract.NavigatorProvider {

    private MainPresenter mainPresenter;
    private MainViewImpl mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = new MainViewImpl(this, null);
        setContentView(mainView.getRootView());
//        setSupportActionBar(mainView.getToolbar());

        mainPresenter = new MainPresenter(mainView);
        mainPresenter.setNavigator(getNavigator(mainPresenter));
        mainView.navigation.selectTab(1);
        setupViewPager();
    }

    private ViewPagerAdapter setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        TestFragment historyFragment = TestFragment.newInstance("History");
        TestFragment statFragment = TestFragment.newInstance("Stat");
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
