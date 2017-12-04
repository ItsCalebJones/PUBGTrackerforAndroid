package me.calebjones.pubgtracker.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class PlayerStat extends RealmObject {

    @PrimaryKey
    private String key;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("mode")
    @Expose
    private String match;
    @SerializedName("stats")
    @Expose
    private RealmList<Stats> stats = null;
    @LinkingObjects("playerStats") // <-- !
    private final RealmResults<User> users = null; // <-- !

    public void setPrimrayKey(){
        key = region + season + match;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public RealmList<Stats> getStats() {
        return stats;
    }

    public void setStats(RealmList<Stats> stats) {
        this.stats = stats;
    }
}