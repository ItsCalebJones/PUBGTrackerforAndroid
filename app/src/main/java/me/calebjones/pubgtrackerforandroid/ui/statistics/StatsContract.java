package me.calebjones.pubgtrackerforandroid.ui.statistics;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import me.calebjones.pubgtrackerforandroid.common.BaseNavigator;
import me.calebjones.pubgtrackerforandroid.common.BasePresenterInterfacce;
import me.calebjones.pubgtrackerforandroid.common.BaseView;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
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

        void showInfoHint(PlaylistView playlistView);

        void showFilterHint();

        void squadVisibility(int visibilityState);

        void duoVisibility(int visibilityState);

        void soloVisibility(int visibilityState);

        void checkHint();
    }

    interface Presenter extends BasePresenterInterfacce {
        void registerEventBus();

        void unRegisterEventBus();

        void onUserEventReceived(UserSelected userSelected);

        void retrieveCachedUser();

        void sortSubmitClicked();

        void resetClicked();

        void checkHint();
    }
}
