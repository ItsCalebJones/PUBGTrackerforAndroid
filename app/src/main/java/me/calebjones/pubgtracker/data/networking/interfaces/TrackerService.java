package me.calebjones.pubgtracker.data.networking.interfaces;

import java.util.List;

import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TrackerService {
    @GET("profile/pc/{pubg-nickname}")
    Call<User> getProfileByName(@Path("pubg-nickname") String name);

    @GET("profile/pc/{pubg-nickname}")
    Call<User> getUserStatsByName(@Path("pubg-nickname") String name,
                                  @Query("region") String region,
                                  @Query("season") String season);

    @GET("search")
    Call<User> getProfileBySteamID(@Query("steamId") int steamId);

    @GET("matches/pc/{account_id}")
    Call<List<Match>> getMatchesByAccountId(@Path("account_id") String accountId,
                                            @Query("region") String region,
                                            @Query("season") String season,
                                            @Query("mode") String mode);
}
