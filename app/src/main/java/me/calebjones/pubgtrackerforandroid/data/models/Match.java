package me.calebjones.pubgtrackerforandroid.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Match extends RealmObject {

    @PrimaryKey
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Updated")
    @Expose
    private Date updated;
    @SerializedName("UpdatedJS")
    @Expose
    private String updatedJS;
    @SerializedName("Season")
    @Expose
    private int season;
    @SerializedName("SeasonDisplay")
    @Expose
    private String seasonDisplay;
    @SerializedName("Match")
    @Expose
    private int match;
    @SerializedName("MatchDisplay")
    @Expose
    private String matchDisplay;
    @SerializedName("Region")
    @Expose
    private int region;
    @SerializedName("RegionDisplay")
    @Expose
    private String regionDisplay;
    @SerializedName("Rounds")
    @Expose
    private int rounds;
    @SerializedName("Wins")
    @Expose
    private int wins;
    @SerializedName("Kills")
    @Expose
    private int kills;
    @SerializedName("Assists")
    @Expose
    private int assists;
    @SerializedName("Top10")
    @Expose
    private int top10;
    @SerializedName("Rating")
    @Expose
    private float rating;
    @SerializedName("RatingChange")
    @Expose
    private float ratingChange;
    @SerializedName("RatingRank")
    @Expose
    private int ratingRank;
    @SerializedName("RatingRankChange")
    @Expose
    private int ratingRankChange;
    @SerializedName("Headshots")
    @Expose
    private int headshots;
    @SerializedName("Kd")
    @Expose
    private float kd;
    @SerializedName("Damage")
    @Expose
    private int damage;
    @SerializedName("TimeSurvived")
    @Expose
    private float timeSurvived;
    @SerializedName("WinRating")
    @Expose
    private int winRating;
    @SerializedName("WinRank")
    @Expose
    private int winRank;
    @SerializedName("WinRatingChange")
    @Expose
    private int winRatingChange;
    @SerializedName("WinRatingRankChange")
    @Expose
    private int winRatingRankChange;
    @SerializedName("KillRating")
    @Expose
    private int killRating;
    @SerializedName("KillRank")
    @Expose
    private int killRank;
    @SerializedName("KillRatingChange")
    @Expose
    private int killRatingChange;
    @SerializedName("KillRatingRankChange")
    @Expose
    private int killRatingRankChange;
    @SerializedName("MoveDistance")
    @Expose
    private float moveDistance;
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