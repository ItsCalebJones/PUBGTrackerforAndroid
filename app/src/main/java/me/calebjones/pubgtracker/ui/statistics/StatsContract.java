package me.calebjones.pubgtracker.ui.statistics;

import android.support.annotation.NonNull;

import me.calebjones.pubgtracker.common.BaseNavigator;
import me.calebjones.pubgtracker.common.BasePresenterInterface;
import me.calebjones.pubgtracker.common.BaseView;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.PlayerStat;


public interface StatsContract {

    interface Navigator extends BaseNavigator {

    }

    interface NavigatorProvider {

        @NonNull
        StatsContract.Navigator getNavigator(StatsContract.Presenter presenter);
    }

    interface View extends BaseView<StatsContract.Presenter> {
        void setViewStateEmpty();

        void createSnackbar(String localizedMessage);

        void configureSoloPlaylist(PlayerStat stat);

        void configureDuoPlaylist(PlayerStat stat);

        void configureSquadPlaylist(PlayerStat stat);

        void showEmpty();

        void showContent();

        void showNoUser();

        int getRegionFilter();

        int getSeasonFilter();

        int getViewModeFilter();

        String getSeason(int position);

        String getRegion(int position);

        int getViewMode(int position);

        void resetFilters();

        void squadVisibility(int visibilityState);

        void duoVisibility(int visibilityState);

        void soloVisibility(int visibilityState);
    }

    interface Presenter extends BasePresenterInterface {
        void registerEventBus();

        void unRegisterEventBus();

        void onUserEventReceived(UserSelected userSelected);

        void retrieveCachedUser();

        void sortSubmitClicked();

        void resetClicked();
    }
}
