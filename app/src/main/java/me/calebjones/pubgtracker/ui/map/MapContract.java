package me.calebjones.pubgtracker.ui.map;

import android.support.annotation.NonNull;

import me.calebjones.pubgtracker.common.BaseNavigator;
import me.calebjones.pubgtracker.common.BasePresenterInterface;
import me.calebjones.pubgtracker.common.BaseView;

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
