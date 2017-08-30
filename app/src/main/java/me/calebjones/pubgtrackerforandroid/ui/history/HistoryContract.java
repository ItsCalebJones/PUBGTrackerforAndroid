package me.calebjones.pubgtrackerforandroid.ui.history;

import android.support.annotation.NonNull;

import java.util.List;

import cz.kinst.jakub.view.SimpleStatefulLayout;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.data.models.User;


public interface HistoryContract {

    interface Navigator extends BaseNavigator {

    }

    interface NavigatorProvider {

        @NonNull
        HistoryContract.Navigator getNavigator(HistoryContract.Presenter presenter);
    }

    interface View extends BaseView<HistoryContract.Presenter> {

        void showEmpty();

        void showContent();

        void showNoUser();

        void setRefreshEnabled(boolean state);

        void createSnackbar(String localizedMessage);

        void setRefreshing(boolean state);

        void setAdapterMatches(RealmResults<Match> matches);

        int getRegionFilter();

        int getSeasonFilter();

        int getModeFilter();

        String getSeason(int position);

        String getRegion(int position);

        String getMode(int position);

        void resetFilters();

        void showFilterHint();
    }

    interface Presenter extends BasePresenterInterfacce {

        void onUserEventReceived(UserSelected userSelected);

        void onRefreshEventReceiver(UserRefreshing state);

        void registerEventBus();

        void unRegisterEventBus();

        void retrieveCachedUser();

        void refreshCurrentUser();

        RealmResults<Match> retrieveMatchHistory(int pubgTrackerId);

        void sortSubmitClicked();

        void resetClicked();

        void checkHint();
    }
}
