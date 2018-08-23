package me.calebjones.pubgtracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

import me.calebjones.pubgtracker.data.enums.PUBGRegion;


@JsonIgnoreProperties(ignoreUnknown = true)
@Type("roster")
public class Roster {

    @Id(CustomIdHandler.class)
    @SerializedName("id")
    @Expose
    private String id;

    @Relationship(value="participants")
    private List<Participant> participant;

    private String won;

    private String shardId;

    private RosterStat stats;

    public List<Participant> getParticipant() {
        return participant;
    }

    public void setParticipant(List<Participant> participant) {
        this.participant = participant;
    }

    public RosterStat getStats() {
        return stats;
    }

    public void setStats(RosterStat stats) {
        this.stats = stats;
    }

    public String getRegionName(){
        return PUBGRegion.findByKey(shardId);
    }

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

}
