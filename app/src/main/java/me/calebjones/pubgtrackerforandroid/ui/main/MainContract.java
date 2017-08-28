package me.calebjones.pubgtrackerforandroid.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.models.User;

public interface MainContract {

    interface Navigator extends BaseNavigator{

        void goOverview();

        void goMatchHistory();

        void goStatistics();

        void goMapActivity();

        void goCompareActivity();
    }


    interface NavigatorProvider {

        @NonNull
        Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void setSupportToolbar(String title);

        void onOverviewClicked();

        void onMatchHistoryClicked();

        void onStatisticsClicked();

        void onPageChaged(int position);

        void closeSearchView();

        void openSearchView();

        void createSnackbar(String message);

        void createSnackbarSetCurrentUser(String message, User user);

        void setUpDrawer(Activity activity, Bundle savedInstanceState);

        void setDrawerUser(User user);
    }

    interface Presenter extends BasePresenterInterfacce {

        boolean searchQuerySubmitted(String query);

        void goOverviewClicked();

        void goMatchHistoryClicked();

        void goStatisticsClicked();

        void setUserAsCurrent(User user);

        void goMapClicked();

        void goCompareClicked();

        void setCurrentUser();

        void getUsers();
    }
}
