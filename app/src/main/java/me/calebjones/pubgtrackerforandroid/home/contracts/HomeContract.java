package me.calebjones.pubgtrackerforandroid.home.contracts;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
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

        void setCurrentRating(String name);

        void showInformationCard();

        void hideInformationCard();

        void showIntroHelper();

        void onInformationCardDismissClicked();

        void setRefreshEnabled(boolean state);

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
