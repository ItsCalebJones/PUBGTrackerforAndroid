package me.calebjones.pubgtracker.data.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public enum PUBGSeason {
    ALL("all", "All"),
    PRE1_2017("2017-pre1", "Early Access Season #1"),
    PRE2_2017("2017-pre2", "Early Access Season #2"),
    PRE3_2017("2017-pre3", "Early Access Season #3"),
    PRE4_2017("2017-pre4", "Early Access Season #4"),
    PRE5_2017("2017-pre5", "Early Access Season #5");

    PUBGSeason(String seasonKey, String seasonName) {
        this.seasonName = seasonName;
        this.seasonKey = seasonKey;
    }

    private String seasonName;

    private String seasonKey;

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonKey() {
        return seasonKey;
    }

    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (PUBGSeason s : PUBGSeason.values()) {
            mMap.put(s.seasonKey, s.seasonName);
        }
        return mMap;
    }

    public static String findByKey(String key) {
        if (mMap.containsKey(key)) {
            return mMap.get(key);
        }
        return null;
    }
}
