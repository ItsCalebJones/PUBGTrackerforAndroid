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
@Type("roster")
public class Roster extends RealmObject {

    @PrimaryKey
    @Id(CustomIdHandler.class)
    @SerializedName("id")
    @Expose
    private String id;

    private String shardId;

    @Relationship("participant")
    private RealmList<Participant> participant;
}
