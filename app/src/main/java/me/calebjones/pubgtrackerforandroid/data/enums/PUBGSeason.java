package me.calebjones.pubgtrackerforandroid.data.enums;

import java.util.HashMap;
import java.util.Map;



public enum PUBGSeason {
    PRE1_2017("2017-pre1", "Early Access Season #1"),
    PRE2_2017("2017-pre2", "Early Access Season #2"),
    PRE3_2017("2017-pre3", "Early Access Season #3");

    PUBGSeason(String seasonName, String seasonKey) {
        this.seasonName = seasonName;
        this.seasonKey = seasonKey;
    }

    private String seasonName;

    private String seasonKey;

    private static final Map<String, String> map;

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonKey() {
        return seasonKey;
    }

    static {
        map = new HashMap<>();
        for (PUBGSeason season : PUBGSeason.values()) {
            map.put(season.getSeasonName(), season.getSeasonKey());
        }
    }

    public static String findByKey(String toFind) {
        return map.get(toFind);
    }
}
