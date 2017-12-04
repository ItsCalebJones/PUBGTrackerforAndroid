package me.calebjones.pubgtracker.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @SerializedName("platform")
    @Expose
    private int platformId;
    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("steamId")
    @Expose
    private String steamId;
    @SerializedName("twitch")
    @Expose
    private String twitch;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("seasonDisplay")
    @Expose
    private String seasonDisplay;
    @SerializedName("LastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("timePlayed")
    @Expose
    private String timePlayed;
    @SerializedName("nickname")
    @Expose
    private String playerName;
    @PrimaryKey
    @SerializedName("pubgTrackerId")
    @Expose
    private int pubgTrackerId;
    @SerializedName("stats")
    @Expose
    private RealmList<PlayerStat> playerStats = null;
    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;
    @SerializedName("MatchHistory")
    @Expose
    private RealmList<Match> matchHistory = null;

    private boolean favoriteUser = false;

    private boolean currentUser = false;

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getTwitch() {
        return twitch;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSeasonDisplay() {
        return seasonDisplay;
    }

    public void setSeasonDisplay(String seasonDisplay) {
        this.seasonDisplay = seasonDisplay;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPubgTrackerId() {
        return pubgTrackerId;
    }

    public void setPubgTrackerId(int pubgTrackerId) {
        this.pubgTrackerId = pubgTrackerId;
    }

    public RealmList<PlayerStat> getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(RealmList<PlayerStat> playerStats) {
        this.playerStats = playerStats;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isFavoriteUser() {
        return favoriteUser;
    }

    public void setFavoriteUser(boolean favoriteUser) {
        this.favoriteUser = favoriteUser;
    }

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }

    public RealmList<Match> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(RealmList<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }
}
