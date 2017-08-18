package me.calebjones.pubgtrackerforandroid.data;


import android.content.Context;

import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import retrofit2.Callback;

public class DataManager {

    private DataSaver dataSaver;

    public DataManager() {
        this.dataSaver = new DataSaver();
    }

    public void getUserByProfileName(String name, Callback<User> callback){
        DataClient.getInstance().getProfileByName(name, callback);
    }

    public void getUserBySteamId(int steamId, Callback<User> callback){
        DataClient.getInstance().getProfileBySteamID(steamId, callback);
    }

    public DataSaver getDataSaver() {
        return dataSaver;
    }
}
