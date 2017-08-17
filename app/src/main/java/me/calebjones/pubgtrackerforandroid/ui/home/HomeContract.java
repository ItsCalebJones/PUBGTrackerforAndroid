package me.calebjones.pubgtrackerforandroid.ui.home;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.data.models.User;


public interface HomeContract {

    interface Navigator extends BaseNavigator {

    }


    interface NavigatorProvider {

        @NonNull
        HomeContract.Navigator getNavigator(Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

        void setProfileAvatar(String name);

        void setProfileName(String name);

        void setCurrentRatingAndRank(String rating, String rank, String kd);

        void showIntroHelper();

        void onInformationCardDismissClicked();

        void setRefreshEnabled(boolean state);

        void setInformationCardVisible(boolean state);

        void setOverviewCardVisible(boolean state);

        void setOverviewSeasonOne(PlayerStat playerStat);

        void setOverviewSeasonTwo(PlayerStat playerStat);

    }

    interface Presenter extends BasePresenterInterfacce {

        void applyUser(User user);

        void onMessageReceived(UserSelected userSelected);

        void registerEventBus();

        void unRegisterEventBus();

        void setInformationCardDismissed(boolean b);

        void retrieveCachedUser();
    }
}
