package me.calebjones.pubgtracker.data.networking.interfaces;

import com.github.jasminb.jsonapi.JSONAPIDocument;

import me.calebjones.pubgtracker.data.models.Match;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PUBGAPIService {


    @GET("/shards/{region}/matches/{match_id}")
    Call<JSONAPIDocument<Match>> getMatchesById(@Path("region") String region,
                                                @Path("match_id") String matchId);

    @GET("/shards/{region}/matches")
    Call<JSONAPIDocument<Match[]>> getMatchesByName(@Path("region") String region,
                                                    @Path("match_id") String matchId,
                                                    @Query("filter[playerNames]") String playerName,
                                                    @Query("filter[gameMode]") String gameMode);

    @GET("/shards/{region}/matches")
    Call<JSONAPIDocument<Match>> getMatchesByRegion(@Path("region") String region);
}
