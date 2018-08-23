package me.calebjones.pubgtracker.data.networking;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import me.calebjones.pubgtracker.TrackerApplication;
import me.calebjones.pubgtracker.data.Config;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.data.models.Asset;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.Participant;
import me.calebjones.pubgtracker.data.models.Roster;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class RetrofitBuilder {

    public static Retrofit getPUBGAPIRetrofit() {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        objectMapper.setDateFormat(df);

        ResourceConverter converter = new ResourceConverter(objectMapper, Match.class, Asset.class, Roster.class, Participant.class);
        converter.enableDeserializationOption(com.github.jasminb.jsonapi.DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);

        JSONAPIConverterFactory jsonapiConverterFactory = new JSONAPIConverterFactory(converter);

        return new Retrofit.Builder()
                .client(getPUBGAPIClient())
                .baseUrl(Config.PUBG_API_BASE_URL)
                .addConverterFactory(jsonapiConverterFactory)
                .build();
    }

    private static OkHttpClient getPUBGAPIClient() {
        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.connectTimeout(15, TimeUnit.SECONDS);
        client.readTimeout(15, TimeUnit.SECONDS);
        client.writeTimeout(15, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", TrackerApplication.getContext().getString(R.string.PUBG_API))
                        .addHeader("Accept", "application/vnd.api+json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return client.build();
    }
}
