package me.calebjones.pubgtrackerforandroid.ui.history;

import android.support.annotation.NonNull;

import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterface;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;


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

        void createSnackbar(String localizedMessage);

        void setAdapterMatches(RealmResults<Match> matches);

        int getRegionFilter();

        int getSeasonFilter();

        int getModeFilter();

        String getSeason(int position);

        String getRegion(int position);

        String getMode(int position);

        void resetFilters();
    }

    interface Presenter extends BasePresenterInterface {

        void onUserEventReceived(UserSelected userSelected);

        void registerEventBus();

        void unRegisterEventBus();

        void retrieveCachedUser();

        RealmResults<Match> retrieveMatchHistory(int pubgTrackerId);

        void sortSubmitClicked();

        void resetClicked();
    }
}
