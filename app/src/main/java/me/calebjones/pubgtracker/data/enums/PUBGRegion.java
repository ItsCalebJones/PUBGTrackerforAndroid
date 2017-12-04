package me.calebjones.pubgtracker.data.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PUBGRegion {
    AAG("agg", "All"),
    NA("na", "[NA] North America"),
    AS("as", "[AS] Asia"),
    EU("eu", "[EU] Europe"),
    OC("oc","[OC] Oceanic"),
    SA("sa","[SA] South America"),
    SEA("sea","[SEA] South East Asia"),
    KRJP("krjp","[KRJP] Korea Japan");


    PUBGRegion(String keyName, String modeName) {
        this.keyName = keyName;
        this.modeName = modeName;
    }

    private String modeName;
    private String keyName;

    public String getRegionName() {
        return modeName;
    }

    public String getKeyName() {
        return keyName;
    }

    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (PUBGRegion s : PUBGRegion.values()) {
            mMap.put(s.keyName, s.modeName);
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
