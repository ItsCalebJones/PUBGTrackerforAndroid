package me.calebjones.pubgtrackerforandroid.data;


import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import retrofit2.Callback;
import timber.log.Timber;

public class DataManager {

    private DataSaver dataSaver;
    private Realm realm = Realm.getDefaultInstance();

    public DataManager() {
        Timber.d("DataManager creating new.");
        this.dataSaver = new DataSaver();
    }

    public void updateUserByProfileName(String name, Callback<User> callback){
        Timber.d("updateUserByProfileName - %s", name);
        DataClient.getInstance().getProfileByName(name, callback);
    }

    public void updateUserBySteamId(int steamId, Callback<User> callback){
        Timber.d("updateUserBySteamId - %s", steamId);
        DataClient.getInstance().getProfileBySteamID(steamId, callback);
    }

    public RealmResults<User> getSavedUsers(){
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
