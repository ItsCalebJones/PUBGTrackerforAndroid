package me.calebjones.pubgtracker.ui.history;

import android.support.annotation.NonNull;

import io.realm.RealmResults;
import me.calebjones.pubgtracker.common.BaseNavigator;
import me.calebjones.pubgtracker.common.BaseView;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.common.BasePresenterInterface;


public interface HistoryContract {

    interface Navigator extends BaseNavigator {

    }

    interface NavigatorProvider {

        @NonNull
        HistoryContract.Navigator getNavigator(HistoryContract.Presenter presenter);
    }

    interface View extends BaseView<Presenter> {

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
