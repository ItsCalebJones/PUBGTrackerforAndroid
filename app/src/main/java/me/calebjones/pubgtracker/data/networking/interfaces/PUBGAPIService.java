package me.calebjones.pubgtracker.data.networking.interfaces;

import com.github.jasminb.jsonapi.JSONAPIDocument;

import java.util.List;

import me.calebjones.pubgtracker.data.models.PUBGMatch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PUBGAPIService {

    @GET("/shards/pc-krjp/matches/{match_id}")
    Call<JSONAPIDocument<PUBGMatch>> getTest(@Path("match_id") String matchId);
}
