package me.calebjones.pubgtracker.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;

public class LiveTracking extends RealmObject {

    @SerializedName("Match")
    @Expose
    private int match;
    @SerializedName("MatchDisplay")
    @Expose
    private String matchDisplay;
    @SerializedName("Season")
    @Expose
    private int season;
    @SerializedName("RegionId")
    @Expose
    private int regionId;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Date")
    @Expose
    private Date date;
    @SerializedName("Delta")
    @Expose
    private float delta;
    @SerializedName("Value")
    @Expose
    private float value;
    @SerializedName("message")
    @Expose
    private String message;

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public String getMatchDisplay() {
        return matchDisplay;
    }

    public void setMatchDisplay(String matchDisplay) {
        this.matchDisplay = matchDisplay;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
