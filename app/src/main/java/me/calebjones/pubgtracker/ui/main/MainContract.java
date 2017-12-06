package me.calebjones.pubgtracker.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import me.calebjones.pubgtracker.common.BaseNavigator;
import me.calebjones.pubgtracker.common.BasePresenterInterface;
import me.calebjones.pubgtracker.common.BaseView;
import me.calebjones.pubgtracker.data.events.UserFavoriteEvent;
import me.calebjones.pubgtracker.data.events.UserRefreshing;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.User;

public interface MainContract {

    interface Navigator extends BaseNavigator{

        void goOverview();

        void goMatchHistory();

        void goStatistics();

        void goMapActivity();

        void goCompareActivity();

        void goToSettings();
    }


    interface NavigatorProvider {

        @NonNull
        Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void setProfileName(String name);

        void setCurrentRatingAndRank(String rating, String rank, String kd);

        void setSupportToolbar(String title);

        void onOverviewClicked();

        void onMatchHistoryClicked();

        void onStatisticsClicked();

        void onPageChaged(int position);

        void closeSearchView();

        void openSearchView();

        void createSnackbar(String message);

        void createErrorSnackbar(String message);

        void createSnackbarSetCurrentUser(String message, User user);

        void setUpDrawer(Activity activity, Bundle savedInstanceState);

        void addDrawerUser(User user);

        void setActiveUser(User user);

        void setUsers(List<User> users);

        void deleteUser(User user);

        void showUserHint(Activity activity);

        void setRefreshing(boolean refreshing);

        void setRefreshEnabled(boolean enable);

        void loadAd();
    }

    interface Presenter extends BasePresenterInterface {

        void onUserSelectedEventReceived(UserSelected userSelected);

        void onUserRefreshingEventReceived(UserRefreshing userRefreshing);

        boolean searchQuerySubmitted(String query);

        void goOverviewClicked();

        void goMatchHistoryClicked();

        void goStatisticsClicked();

        void setUserAsCurrent(User user);

        void goMapClicked();

        void goCompareClicked();

        void setCurrentUser();

        void getUsers();

        void onUserFavoriteEvent(UserFavoriteEvent userFavoriteEvent);

        void setCurrentUser(long identifier);

        void goToSettings();

        void setFavoriteUserState(boolean favoriteUser);

        void refreshCurrentUser();
    }
}
