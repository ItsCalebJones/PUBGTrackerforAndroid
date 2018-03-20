package me.calebjones.pubgtracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ALPCJONESM2 on 3/19/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Type("match")
public class PUBGMatch extends RealmObject {

    @PrimaryKey
    @Id(CustomIdHandler.class)
    @SerializedName("id")
    @Expose
    private String id;

    private String gameMode;

    @Relationship(value="rosters")
    private RealmList<Roster> rosters;

    @Relationship(value="assets")
    private RealmList<Asset> assets;

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
