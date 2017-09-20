package me.calebjones.pubgtrackerforandroid.ui.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;


public class MapViewImpl implements MapContract.View, AHBottomNavigation.OnTabSelectedListener {

    private View mRootView;
    private Context context;
    private MapContract.Presenter mapPresenter;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
//            ActionBar actionBar = context.getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
            navigation.restoreBottomNavigation(true);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @BindView(R.id.navigation_view)
    AHBottomNavigation navigation;
    @BindView(R.id.fullscreen_map)
    SubsamplingScaleImageView imageView;
    @BindView(R.id.map_coordinator)
    CoordinatorLayout coordinator;


    public MapViewImpl(Context context, ViewGroup container){
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.activity_map, container);
        ButterKnife.bind(this, mRootView);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        AHBottomNavigationItem erangelItem = new AHBottomNavigationItem
                ("Erangel", ContextCompat.getDrawable(context, R.drawable.erangel),
                        ContextCompat.getColor(context, R.color.primary));

        AHBottomNavigationItem desertItem = new AHBottomNavigationItem
                ("Desert (TBD)",
                        ContextCompat.getDrawable(context, R.drawable.desert),
                        ContextCompat.getColor(context, R.color.accent));

        navigation.addItem(erangelItem);
        navigation.addItem(desertItem);
        navigation.setColored(true);
        navigation.setOnTabSelectedListener(this);

        mVisible = true;

        // Set up the user interaction to manually show or hide the system UI.
        coordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });


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
    public void setPresenter(MapContract.Presenter presenter) {
        mapPresenter = presenter;
    }


    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        navigation.hideBottomNavigation(true);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        navigation.restoreBottomNavigation(true);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }
    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        switch (position) {
            case 0:
                imageView.setImage(ImageSource.resource(R.drawable.erangel_map));
                return true;
            case 1:
                imageView.setImage(ImageSource.resource(R.drawable.desert_map));
                return true;
            default:
                return false;
        }
    }
}
