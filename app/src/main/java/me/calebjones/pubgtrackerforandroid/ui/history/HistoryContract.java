package me.calebjones.pubgtrackerforandroid.ui.history;

import android.support.annotation.NonNull;

import cz.kinst.jakub.view.SimpleStatefulLayout;
import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;


public interface HistoryContract {

    interface Navigator extends BaseNavigator {

    }

    interface NavigatorProvider {

        @NonNull
        HistoryContract.Navigator getNavigator(HistoryContract.Presenter presenter);
    }

    interface View extends BaseView<HistoryContract.Presenter> {

        void setViewState(SimpleStatefulLayout.State state);

        void setRefreshEnabled(boolean state);

        void createSnackbar(String localizedMessage);

        void setRefreshing(boolean state);
    }

    interface Presenter extends BasePresenterInterfacce {

        void applyUser(User user);

        void onUserEventReceived(UserSelected userSelected);

        void onRefreshEventReceiver(UserRefreshing state);

        void registerEventBus();

        void unRegisterEventBus();

        void retrieveCachedUser();

        void refreshCurrentUser();

        void retrieveMatchHistoryFiltered();
    }
}
