package me.calebjones.pubgtracker.data.networking.interfaces;

import com.github.jasminb.jsonapi.JSONAPIDocument;

import java.util.List;

import me.calebjones.pubgtracker.data.models.PUBGMatch;
import retrofit2.Call;
import retrofit2.http.GET;


public interface PUBGAPIService {

    @GET("/shards/pc-na/matches")
    Call<JSONAPIDocument<List<PUBGMatch>>> getTest();
}
