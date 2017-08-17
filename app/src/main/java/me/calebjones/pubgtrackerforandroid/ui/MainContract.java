package me.calebjones.pubgtrackerforandroid.ui;


import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;

public interface MainContract {

    interface Navigator extends BaseNavigator{

        void goHome();

        void goMatchHistory();

        void goStatistics();
    }


    interface NavigatorProvider {

        @NonNull
        Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void setSupportToolbar(String title);

        void onHomeClicked();

        void onMatchHistoryClicked();

        void onStatisticsClicked();

        void onPageChaged(int position);

        void closeSearchView();

        void openSearchView();

        void createSnackbar(String message);

    }

    interface Presenter extends BasePresenterInterfacce {

        boolean searchQuerySubmitted(String query);

        void goHomeClicked();

        void goMatchHistoryClicked();

        void goStatisticsClicked();

    }
}
