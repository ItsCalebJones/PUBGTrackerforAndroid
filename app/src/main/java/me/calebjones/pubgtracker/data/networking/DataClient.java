package me.calebjones.pubgtracker.data.networking;

import com.github.jasminb.jsonapi.JSONAPIDocument;

import java.util.List;

import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.tracker.TrackerMatch;
import me.calebjones.pubgtracker.data.models.tracker.User;
import me.calebjones.pubgtracker.data.networking.interfaces.PUBGAPIService;
import me.calebjones.pubgtracker.data.networking.interfaces.TrackerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;



public class DataClient {

    private final TrackerService apiService;
    private final PUBGAPIService pubgAPIService;
    private static DataClient mInstance;
    private Retrofit apiRetrofit;
    private Retrofit pubgAPIRetrofit;

    private DataClient(){
        apiRetrofit = RetrofitBuilder.getTrackerAPIRetrofit();
        apiService = apiRetrofit.create(TrackerService.class);

        pubgAPIRetrofit = RetrofitBuilder.getPUBGAPIRetrofit();
        pubgAPIService = pubgAPIRetrofit.create(PUBGAPIService.class);
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


    public Call<JSONAPIDocument<Match>> getTest (String matchId, Callback<JSONAPIDocument<Match>> callback){
        Call<JSONAPIDocument<Match>> call = pubgAPIService.getMatchesById(matchId);
        call.enqueue(callback);
        return call;
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

    public Call<List<TrackerMatch>>  getMatchHistoryByAccountId(User user, String region, String mode, String season, Callback<List<TrackerMatch>> callback){
        Call<List<TrackerMatch>> call = apiService.getMatchesByAccountId(user.getAccountId(), region, season, mode);
        call.enqueue(callback);
        return call;
    }

    public Call<List<TrackerMatch>>  getMatchHistoryByAccountId(User user, Callback<List<TrackerMatch>> callback){
        Call<List<TrackerMatch>> call = apiService.getMatchesByAccountId(user.getAccountId(), null, null, null);
        call.enqueue(callback);
        return call;
    }

    public Call<User> getUserStatsByName(User user, String region, String season, Callback<User> callback){
        Call<User> call = apiService.getUserStatsByName(user.getPlayerName(), region, season);
        call.enqueue(callback);
        return call;
    }
}
