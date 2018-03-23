package me.calebjones.pubgtracker.data.models;

import io.realm.RealmObject;

/**
 * Created by ALPCJONESM2 on 3/20/18.
 */

public class RosterStat extends RealmObject {

    private int rank;

    private int teamId;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
