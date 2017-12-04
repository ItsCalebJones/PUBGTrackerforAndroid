package me.calebjones.pubgtracker.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Match extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("updated")
    @Expose
    private Date updated;
    @SerializedName("updatedJS")
    @Expose
    private String updatedJS;
    @SerializedName("season")
    @Expose
    private int season;
    @SerializedName("seasonDisplay")
    @Expose
    private String seasonDisplay;
    @SerializedName("match")
    @Expose
    private int match;
    @SerializedName("matchDisplay")
    @Expose
    private String matchDisplay;
    @SerializedName("region")
    @Expose
    private int region;
    @SerializedName("regionDisplay")
    @Expose
    private String regionDisplay;
    @SerializedName("rounds")
    @Expose
    private int rounds;
    @SerializedName("wins")
    @Expose
    private int wins;
    @SerializedName("kills")
    @Expose
    private int kills;
    @SerializedName("assists")
    @Expose
    private int assists;
    @SerializedName("top10")
    @Expose
    private int top10;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("ratingChange")
    @Expose
    private float ratingChange;
    @SerializedName("ratingRank")
    @Expose
    private int ratingRank;
    @SerializedName("ratingRankChange")
    @Expose
    private int ratingRankChange;
    @SerializedName("headshots")
    @Expose
    private int headshots;
    @SerializedName("kd")
    @Expose
    private float kd;
    @SerializedName("damage")
    @Expose
    private int damage;
    @SerializedName("timeSurvived")
    @Expose
    private float timeSurvived;
    @SerializedName("winRating")
    @Expose
    private int winRating;
    @SerializedName("winRank")
    @Expose
    private int winRank;
    @SerializedName("winRatingChange")
    @Expose
    private int winRatingChange;
    @SerializedName("winRatingRankChange")
    @Expose
    private int winRatingRankChange;
    @SerializedName("killRating")
    @Expose
    private int killRating;
    @SerializedName("killRank")
    @Expose
    private int killRank;
    @SerializedName("killRatingChange")
    @Expose
    private int killRatingChange;
    @SerializedName("killRatingRankChange")
    @Expose
    private int killRatingRankChange;
    @SerializedName("moveDistance")
    @Expose
    private float moveDistance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    @LinkingObjects("matchHistory") // <-- !
    private final RealmResults<User> users = null; // <-- !

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getUpdatedJS() {
        return updatedJS;
    }

    public void setUpdatedJS(String updatedJS) {
        this.updatedJS = updatedJS;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getSeasonDisplay() {
        return seasonDisplay;
    }

    public void setSeasonDisplay(String seasonDisplay) {
        this.seasonDisplay = seasonDisplay;
    }

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

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getRegionDisplay() {
        return regionDisplay;
    }

    public void setRegionDisplay(String regionDisplay) {
        this.regionDisplay = regionDisplay;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getTop10() {
        return top10;
    }

    public void setTop10(int top10) {
        this.top10 = top10;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(float ratingChange) {
        this.ratingChange = ratingChange;
    }

    public int getRatingRank() {
        return ratingRank;
    }

    public void setRatingRank(int ratingRank) {
        this.ratingRank = ratingRank;
    }

    public int getRatingRankChange() {
        return ratingRankChange;
    }

    public void setRatingRankChange(int ratingRankChange) {
        this.ratingRankChange = ratingRankChange;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public float getKd() {
        return kd;
    }

    public void setKd(float kd) {
        this.kd = kd;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getTimeSurvived() {
        return timeSurvived;
    }

    public void setTimeSurvived(float timeSurvived) {
        this.timeSurvived = timeSurvived;
    }

    public int getWinRating() {
        return winRating;
    }

    public void setWinRating(int winRating) {
        this.winRating = winRating;
    }

    public int getWinRank() {
        return winRank;
    }

    public void setWinRank(int winRank) {
        this.winRank = winRank;
    }

    public int getWinRatingChange() {
        return winRatingChange;
    }

    public void setWinRatingChange(int winRatingChange) {
        this.winRatingChange = winRatingChange;
    }

    public int getWinRatingRankChange() {
        return winRatingRankChange;
    }

    public void setWinRatingRankChange(int winRatingRankChange) {
        this.winRatingRankChange = winRatingRankChange;
    }

    public int getKillRating() {
        return killRating;
    }

    public void setKillRating(int killRating) {
        this.killRating = killRating;
    }

    public int getKillRank() {
        return killRank;
    }

    public void setKillRank(int killRank) {
        this.killRank = killRank;
    }

    public int getKillRatingChange() {
        return killRatingChange;
    }

    public void setKillRatingChange(int killRatingChange) {
        this.killRatingChange = killRatingChange;
    }

    public int getKillRatingRankChange() {
        return killRatingRankChange;
    }

    public void setKillRatingRankChange(int killRatingRankChange) {
        this.killRatingRankChange = killRatingRankChange;
    }

    public float getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(float moveDistance) {
        this.moveDistance = moveDistance;
    }
}