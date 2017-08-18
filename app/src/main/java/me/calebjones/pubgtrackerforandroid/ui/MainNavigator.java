package me.calebjones.pubgtrackerforandroid.ui;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainNavigator implements MainContract.Navigator {

    private AppCompatActivity homeActivity;
    private ViewPager mViewPager;

    public MainNavigator(AppCompatActivity activity, ViewPager viewpager){
        homeActivity = activity;
        mViewPager = viewpager;
    }

    @Override
    public void goStatistics() {
        mViewPager.setCurrentItem(0);

    }

    @Override
    public void goHome() {
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void goMatchHistory() {
        mViewPager.setCurrentItem(2);
    }
}
