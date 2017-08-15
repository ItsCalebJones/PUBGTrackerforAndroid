package me.calebjones.pubgtrackerforandroid.home;


import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.common.BaseView;

public interface MainContract {

    interface Navigator extends BaseNavigator{

        void goHome();

        void goMatchHistory();

        void goStatistics();
    }


    interface NavigatorProvider {

        @NonNull
        Navigator getNavigator(MainContract.Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void setSupportToolbar(String title);

        void onHomeClicked();

        void onMatchHistoryClicked();

        void onStatisticsClicked();

        void closeSearchView();

        void openSearchView();

        void createSnackbar(String message);

    }

    interface Presenter extends BasePresenter {

        boolean searchQuerySubmitted(String query);

        void goHomeClicked();

        void goMatchHistoryClicked();

        void goStatisticsClicked();

    }
}
