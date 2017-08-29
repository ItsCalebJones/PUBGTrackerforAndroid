package me.calebjones.pubgtrackerforandroid.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @SerializedName("platformId")
    @Expose
    private int platformId;
    @SerializedName("AccountId")
    @Expose
    private String accountId;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("selectedRegion")
    @Expose
    private String selectedRegion;
    @SerializedName("defaultSeason")
    @Expose
    private String defaultSeason;
    @SerializedName("seasonDisplay")
    @Expose
    private String seasonDisplay;
    @SerializedName("LastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("LiveTracking")
    @Expose
    private RealmList<LiveTracking> liveTracking = null;
    @SerializedName("PlayerName")
    @Expose
    private String playerName;
    @PrimaryKey
    @SerializedName("PubgTrackerId")
    @Expose
    private int pubgTrackerId;
    @SerializedName("Stats")
    @Expose
    private RealmList<PlayerStat> playerStats = null;
    @SerializedName("MatchHistory")
    @Expose
    private RealmList<Match> matchHistory = null;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    private boolean favoriteUser = false;

    private boolean currentUser = false;

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isFavoriteUser() {
        return favoriteUser;
    }

    public void setFavoriteUser(boolean favoriteUser) {
        this.favoriteUser = favoriteUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public String getDefaultSeason() {
        return defaultSeason;
    }

    public void setDefaultSeason(String defaultSeason) {
        this.defaultSeason = defaultSeason;
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

    public RealmList<LiveTracking> getLiveTracking() {
        return liveTracking;
    }

    public void setLiveTracking(RealmList<LiveTracking> liveTracking) {
        this.liveTracking = liveTracking;
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

    public RealmList<Match> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(RealmList<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }
}
