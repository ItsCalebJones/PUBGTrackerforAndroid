package me.calebjones.pubgtrackerforandroid.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PlayerStat extends RealmObject {

    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Season")
    @Expose
    private String season;
    @SerializedName("Match")
    @Expose
    private String match;
    @SerializedName("Stats")
    @Expose
    private RealmList<Stats> stats = null;

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