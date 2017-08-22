package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;


public interface StatsContract {

    interface Navigator extends BaseNavigator {

    }

    interface NavigatorProvider {

        @NonNull
        StatsContract.Navigator getNavigator(StatsContract.Presenter presenter);
    }

    interface View extends BaseView<StatsContract.Presenter> {

    }

    interface Presenter extends BasePresenterInterfacce {

    }
}
