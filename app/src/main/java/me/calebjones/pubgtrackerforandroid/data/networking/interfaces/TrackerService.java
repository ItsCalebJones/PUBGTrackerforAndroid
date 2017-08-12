package me.calebjones.pubgtrackerforandroid.data.networking.interfaces;

import me.calebjones.pubgtrackerforandroid.data.models.APIResponse;
import me.calebjones.pubgtrackerforandroid.data.networking.response.TrackerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TrackerService {
    @GET("profile/pc/{pubg-nickname}")
    Call<APIResponse> getProfileByName(@Path("pubg-nickname") String name);

    @GET("search")
    Call<APIResponse> getProfileBySteamID(@Query("steamId") int steamId);
}
