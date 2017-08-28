package me.calebjones.pubgtrackerforandroid.data;


import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import retrofit2.Callback;

public class DataManager {

    private DataSaver dataSaver;
    private Realm realm = Realm.getDefaultInstance();

    public DataManager() {
        this.dataSaver = new DataSaver();
    }

    public void updateUserByProfileName(String name, Callback<User> callback){
        DataClient.getInstance().getProfileByName(name, callback);
    }

    public void updateUserBySteamId(int steamId, Callback<User> callback){
        DataClient.getInstance().getProfileBySteamID(steamId, callback);
    }

    public RealmResults<User> getSavedUsers(){
        return realm.where(User.class).equalTo("savedUser", true).findAll();
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }
}
