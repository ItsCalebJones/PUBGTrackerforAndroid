package me.calebjones.pubgtracker.data;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import me.calebjones.pubgtracker.data.models.tracker.TrackerMatch;
import me.calebjones.pubgtracker.data.models.tracker.User;
import me.calebjones.pubgtracker.data.networking.DataClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DataManager {

    private static DataManager mInstance;
    private DataSaver dataSaver;
    private Realm realm = Realm.getDefaultInstance();

    public DataManager() {
        Timber.d("DataManager creating new.");
        this.dataSaver = new DataSaver();
    }

    /**
     * Applications must call create to configure the DataClient singleton
     */
    public static void create() {
        mInstance = new DataManager();
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            throw new AssertionError("Did you forget to call create() ?");
        }
        return mInstance;
    }

    public void updateUserByProfileName(String name, Callback<User> callback) {
        Timber.d("updateUserByProfileName - %s", name);
        DataClient.getInstance().getProfileByName(name, callback);
    }

    public void getUserMatchHistory(final User user) {
        Timber.d("getUserMatchHistory - %s", user.getPlayerName());
        DataClient.getInstance().getMatchHistoryByAccountId(user, new Callback<List<TrackerMatch>>() {
            @Override
            public void onResponse(Call<List<TrackerMatch>> call, Response<List<TrackerMatch>> response) {
                if (response.isSuccessful()) {
                    List<TrackerMatch> matches = response.body();
                    DataSaver dataSaver = new DataSaver();
                    dataSaver.saveMatches(matches, user);
                }
            }

            @Override
            public void onFailure(Call<List<TrackerMatch>> call, Throwable t) {
                Timber.e(t);
            }
        });
    }

    public void updateUserBySteamId(int steamId, Callback<User> callback) {
        Timber.d("updateUserBySteamId - %s", steamId);
        DataClient.getInstance().getProfileBySteamID(steamId, callback);
    }

    public RealmResults<User> getSavedUsers() {
        Timber.d("getSavedUsers - Returning list of favorite users.");
        return realm.where(User.class).equalTo("favoriteUser", true).findAll();
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }

    public User getCurrentUser() {
        Timber.d("getCurrentUser - Returning user.");
        return realm.where(User.class).equalTo("currentUser", true).findFirst();
    }

    public User getUserByID(int identifier) {
        Timber.d("getUserByID - %s", identifier);
        return realm.where(User.class).equalTo("pubgTrackerId", identifier).findFirst();
    }

}
