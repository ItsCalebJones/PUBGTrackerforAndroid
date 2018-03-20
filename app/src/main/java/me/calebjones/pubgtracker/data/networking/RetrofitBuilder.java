package me.calebjones.pubgtracker.data.networking;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.retrofit.JSONAPIConverterFactory;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.realm.RealmObject;
import me.calebjones.pubgtracker.TrackerApplication;
import me.calebjones.pubgtracker.data.Config;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.data.models.Asset;
import me.calebjones.pubgtracker.data.models.PUBGMatch;
import me.calebjones.pubgtracker.data.models.Participant;
import me.calebjones.pubgtracker.data.models.RealmStr;
import me.calebjones.pubgtracker.data.models.Roster;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public static Retrofit getTrackerAPIRetrofit() {

        return new Retrofit.Builder()
                .client(defaultClient())
                .baseUrl(Config.TRACKER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public static Retrofit getPUBGAPIRetrofit() {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResourceConverter converter = new ResourceConverter(objectMapper, PUBGMatch.class, Asset.class, Roster.class, Participant.class);
        converter.enableDeserializationOption(com.github.jasminb.jsonapi.DeserializationFeature.ALLOW_UNKNOWN_INCLUSIONS);

        JSONAPIConverterFactory jsonapiConverterFactory = new JSONAPIConverterFactory(converter);

        return new Retrofit.Builder()
                .client(getPUBGAPIClient())
                .baseUrl(Config.PUBG_API_BASE_URL)
                .addConverterFactory(jsonapiConverterFactory)
                .build();
    }

    private static OkHttpClient defaultClient() {
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
                        .addHeader("TRN-Api-Key", TrackerApplication.getContext().getString(R.string.pubg_tracker_key));

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return client.build();
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

    private static Gson getGson(){
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(getToken(), new TypeAdapter<RealmList<RealmStr>>() {

                    @Override
                    public void write(JsonWriter out, RealmList<RealmStr> value) throws io.realm.internal.IOException {
                        // Ignore
                    }

                    @Override
                    public RealmList<RealmStr> read(JsonReader in) throws io.realm.internal.IOException, java.io.IOException {
                        RealmList<RealmStr> list = new RealmList<RealmStr>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmStr(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();
    }

    private static Type getToken(){
        return new TypeToken<RealmList<RealmStr>>() {}.getType();
    }

}
