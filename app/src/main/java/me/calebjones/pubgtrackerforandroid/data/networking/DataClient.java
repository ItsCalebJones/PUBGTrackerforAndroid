package me.calebjones.pubgtrackerforandroid.data.networking;

import me.calebjones.pubgtrackerforandroid.data.models.User;
import me.calebjones.pubgtrackerforandroid.data.networking.interfaces.TrackerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by Caleb on 8/12/2017.
 */

public class DataClient {

    private final TrackerService apiService;
    private static DataClient mInstance;
    private Retrofit apiRetrofit;

    private DataClient(){
        apiRetrofit = RetrofitBuilder.getAPIRetrofit();
        apiService = apiRetrofit.create(TrackerService.class);
    }

    /**
     * Applications must call create to configure the DataClient singleton
     */
    public static void create() {
        mInstance = new DataClient();
    }

    public static DataClient getInstance() {
        if (mInstance == null) {
            throw new AssertionError("Did you forget to call create() ?");
        }
        return mInstance;
    }

    public Call<User> getProfileByName(String name, Callback<User> callback){
        Call<User> call = apiService.getProfileByName(name);
        call.enqueue(callback);
        return call;
    }

    public Call<User> getProfileBySteamID(int steamID, Callback<User> callback){
        Call<User> call = apiService.getProfileBySteamID(steamID);
        call.enqueue(callback);
        return call;
    }
}
