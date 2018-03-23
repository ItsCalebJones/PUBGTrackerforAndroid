package me.calebjones.pubgtracker.ui.overview;

import android.support.annotation.NonNull;

import me.calebjones.pubgtracker.common.BaseNavigator;
import me.calebjones.pubgtracker.common.BaseView;
import me.calebjones.pubgtracker.data.events.UserRefreshing;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.tracker.TrackerMatch;
import me.calebjones.pubgtracker.data.models.tracker.User;
import me.calebjones.pubgtracker.common.BasePresenterInterface;
import me.calebjones.pubgtracker.data.models.tracker.PlayerStat;


public interface OverviewContract {

    interface Navigator extends BaseNavigator {
        void showIntro();
    }


    interface NavigatorProvider {

        @NonNull
        OverviewContract.Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void showIntroHelper();

        void onInformationCardDismissClicked();

        void setInformationCardVisible(boolean state);

        void setOverviewCardVisible(boolean state);

        void setMatchCardVisible(boolean state);

        void setMatchCardContent(TrackerMatch match);

        void setOverviewContent(PlayerStat playerStat);

        void setOverviewSeasonTwo(PlayerStat playerStat);

        void setOverviewSeasonOneVisible(boolean state);

        void setOverviewSeasonTwoVisible(boolean state);

        void createSnackbar(String localizedMessage);

        void showEmpty();

        void showContent();

        void showNoUser();

    }

    interface Presenter extends BasePresenterInterface {

        void applyUser(User user);

        void onUserEventReceived(UserSelected userSelected);

        void onRefreshEventReceiver(UserRefreshing state);

        void registerEventBus();

        void unRegisterEventBus();

        void setInformationCardDismissed(boolean b);

        void retrieveCachedUser();

        void showIntro();
    }
}
