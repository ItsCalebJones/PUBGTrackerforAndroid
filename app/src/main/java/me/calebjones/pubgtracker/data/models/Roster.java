package me.calebjones.pubgtracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jasminb.jsonapi.annotations.Id;
<<<<<<< HEAD
=======
import com.github.jasminb.jsonapi.annotations.Meta;
>>>>>>> origin/PUBGApi
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


@JsonIgnoreProperties(ignoreUnknown = true)
@Type("roster")
public class Roster extends RealmObject {

    @PrimaryKey
    @Id(CustomIdHandler.class)
    @SerializedName("id")
    @Expose
    private String id;

    private String shardId;

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    private String won;

    @Relationship(value="participants")
    private RealmList<Participant> participant;
}
