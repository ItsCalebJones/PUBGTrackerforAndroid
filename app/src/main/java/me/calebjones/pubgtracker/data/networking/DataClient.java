package me.calebjones.pubgtracker.data.networking;

import com.github.jasminb.jsonapi.JSONAPIDocument;

import java.util.List;

import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.networking.interfaces.PUBGAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;



public class DataClient {


    private final PUBGAPIService pubgAPIService;
    private static DataClient mInstance;
    private Retrofit pubgAPIRetrofit;

    private DataClient(){
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


    public Call<JSONAPIDocument<Match[]>> getPlayersMatchesByPlayerName (String shardId, String playerName, String gameMode, Callback<JSONAPIDocument<Match[]>> callback){
        Call<JSONAPIDocument<Match[]>> call = pubgAPIService.getMatchesByName(shardId, playerName, gameMode);
        call.enqueue(callback);
        return call;
    }

    public Call<JSONAPIDocument<Match>> getMatchByID (String shardId, String matchId, Callback<JSONAPIDocument<Match>> callback){
        Call<JSONAPIDocument<Match>> call = pubgAPIService.getMatchesById(shardId, matchId);
        call.enqueue(callback);
        return call;
    }

}
