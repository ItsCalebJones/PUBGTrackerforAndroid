package me.calebjones.pubgtrackerforandroid.data.networking.interfaces;

import me.calebjones.pubgtrackerforandroid.data.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TrackerService {
    @GET("profile/pc/{pubg-nickname}")
    Call<User> getProfileByName(@Path("pubg-nickname") String name);

    @GET("search")
    Call<User> getProfileBySteamID(@Query("steamId") int steamId);
}
