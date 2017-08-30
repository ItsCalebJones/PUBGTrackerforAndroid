package me.calebjones.pubgtrackerforandroid.ui.main;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.calebjones.pubgtrackerforandroid.ui.settings.SettingsActivity;

public class MainNavigator implements MainContract.Navigator {

    private AppCompatActivity homeActivity;
    private ViewPager mViewPager;

    public MainNavigator(AppCompatActivity activity, ViewPager viewpager){
        homeActivity = activity;
        mViewPager = viewpager;
    }

    @Override
    public void goMapActivity() {

    }

    @Override
    public void goCompareActivity() {

    }

    @Override
    public void goToSettings() {
        homeActivity.startActivity(new Intent(homeActivity, SettingsActivity.class));
    }

    @Override
    public void goStatistics() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void goOverview() {
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void goMatchHistory() {
        mViewPager.setCurrentItem(2);
    }
}
