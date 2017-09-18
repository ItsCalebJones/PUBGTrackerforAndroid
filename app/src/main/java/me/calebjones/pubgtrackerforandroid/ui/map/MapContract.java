package me.calebjones.pubgtrackerforandroid.ui.map;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterface;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.ui.main.MainContract;

/**
 * Created by Caleb on 9/16/2017.
 */

public interface MapContract {
    interface Navigator extends BaseNavigator{

    }

    interface NavigatorProvider{
        @NonNull
        Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenterInterface{

    }
}
