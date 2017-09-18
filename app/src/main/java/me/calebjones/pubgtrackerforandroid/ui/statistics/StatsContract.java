package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.support.annotation.NonNull;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterface;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.ui.views.PlaylistView;


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

        String getSeason(int position);

        String getRegion(int position);

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
