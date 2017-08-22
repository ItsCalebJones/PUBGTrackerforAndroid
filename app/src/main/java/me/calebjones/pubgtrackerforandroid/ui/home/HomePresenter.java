package me.calebjones.pubgtrackerforandroid.ui.home;

import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.common.BasePresenter;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.data.DataManager;
import me.calebjones.pubgtrackerforandroid.data.events.UserRefreshing;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private final HomeContract.View homeView;
    private User currentUser;
    private DataManager dataManager;

    public HomePresenter(HomeContract.View view) {
        homeView = view;
        homeView.setPresenter(this);
        dataManager = new DataManager();

    }

    @Override
    public void applyUser(User user) {
        currentUser = user;
        configureOverviewCard(user);
        configureMatchCard(user);
    }

    private void configureMatchCard(User user) {
        homeView.setMatchCardVisible(true);
        homeView.setMatchCardContent(user.getMatchHistory().get(0));
    }

    private void configureOverviewCard(User user) {
        homeView.setOverviewCardVisible(true);
        PlayerStat highestElo = null;
        for (PlayerStat playerStat : user.getStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    !Objects.equals(playerStat.getRegion(), "agg")) {
                float elo = playerStat.getStats().get(9).getValueDec();
                if (highestElo == null) {
                    highestElo = playerStat;
                } else if (highestElo.getStats().get(9).getValueDec() < elo) {
                    highestElo = playerStat;
                }
            }
            if (highestElo != null) {
                homeView.setOverviewContent(highestElo);
            } else {
                homeView.setOverviewSeasonOneVisible(false);
            }
        }

            homeView.setProfileAvatar(user.getAvatar());
            homeView.setProfileName(user.getPlayerName());
            homeView.setCurrentRatingAndRank(
                    highestElo.getStats().get(9).getValue(),
                    String.valueOf(highestElo.getStats().get(9).getRank()),
                    findKD(user));
            homeView.setDefaultUserIcon(user.isDefaultUser());
    }

    private String findKD(User user) {
        Float highestKD = null;
        for (PlayerStat playerStat : user.getStats()) {
            if (Objects.equals(playerStat.getSeason(), user.getDefaultSeason()) &&
                    Objects.equals(playerStat.getRegion(), "agg")) {
                float currentKd = playerStat.getStats().get(0).getValueDec();
                if (highestKD == null) {
                    highestKD = currentKd;
                } else if (highestKD < currentKd) {
                    highestKD = currentKd;
                }
            }
        }
        if (highestKD != null) {
            return String.valueOf(highestKD);
        } else {
            return "Unknown";
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUserEventReceived(UserSelected userSelected) {
        homeView.setRefreshEnabled(true);
        applyUser(userSelected.response);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onRefreshEventReceiver(UserRefreshing state) {
        homeView.setRefreshing(state.refreshing);
    }

    @Override
    public void registerEventBus() {
        Timber.v("Checking EventBus...");
        if (!EventBus.getDefault().isRegistered(this)) {
            Timber.v("Registering EventBus");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void unRegisterEventBus() {
        Timber.v("Unregistering EventBus");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setInformationCardDismissed(boolean state) {
        Prefs.putBoolean(Config.PREF_INFORMATION_CARD_DISMISSED, state);
    }

    @Override
    public void retrieveCachedUser() {
        User user = getRealm().where(User.class).equalTo("defaultUser", true).findFirst();
        if (user != null) {
            applyUser(user);
            homeView.setRefreshEnabled(true);
        } else {
            homeView.setRefreshEnabled(false);
        }
    }

    @Override
    public void setDefaultUserState(boolean state) {
        if (state) {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<User> users = realm.where(User.class).equalTo("defaultUser", true).findAll();
                    for (User defaultUser : users) {
                        defaultUser.setDefaultUser(false);
                        realm.copyToRealmOrUpdate(defaultUser);
                    }
                    currentUser.setDefaultUser(true);
                    realm.copyToRealmOrUpdate(currentUser);
                }
            });
        } else {
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<User> users = realm.where(User.class).equalTo("defaultUser", true).findAll();
                    for (User defaultUser : users) {
                        defaultUser.setDefaultUser(false);
                        realm.copyToRealmOrUpdate(defaultUser);
                    }
                }
            });
        }
    }

    @Override
    public void refreshCurrentUser() {
        dataManager.getUserByProfileName(currentUser.getPlayerName(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    if (user != null) {
                        if (user.getError() != null && user.getMessage() != null) {
                            homeView.createSnackbar(user.getMessage());
                        } else if (user.getPlayerName() != null) {
                            dataManager.getDataSaver().save(user);
                        }
                    }
                } else {
                    homeView.createSnackbar(response.message());
                    Timber.e(response.message());
                }
                homeView.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Timber.e(t);
                homeView.createSnackbar(t.getLocalizedMessage());
                homeView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onStart() {
        Timber.v("onStart");
        registerEventBus();
    }

    @Override
    public void onStop() {
        Timber.v("onStop");
        unRegisterEventBus();
    }

    @Override
    public void onResume() {
        Timber.v("onResume");
        registerEventBus();
    }
}
