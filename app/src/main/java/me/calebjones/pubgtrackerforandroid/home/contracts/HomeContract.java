package me.calebjones.pubgtrackerforandroid.home.contracts;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;


public interface HomeContract {

    interface Navigator extends BaseNavigator {

    }


    interface NavigatorProvider {

        @NonNull
        HomeContract.Navigator getNavigator(HomeContract.Presenter presenter);
    }

    interface View extends BaseView<HomeContract.Presenter> {

        void setProfileAvatar(String name);
        void setProfileName(String name);
        void setCurrentRating(String name);
        void showInformationCard();
        void hideInformationCard();
        void showIntroHelper();
        void onInformationCardDismissClicked();

    }

    interface Presenter extends BasePresenter {

        void onMessageReceived(UserSelected userSelected);

        void registerEventBus();

        void unRegisterEventBus();

        void setInformationCardDismissed(boolean b);
    }
}
