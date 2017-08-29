package me.calebjones.pubgtrackerforandroid.data.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PUBGMode {
    ALL("agg", "All"),
    SOLO("solo", "Solo"),
    DUO("duo", "Duo"),
    SQUAD("squad","Squad");

    PUBGMode(String keyName, String modeName) {
        this.keyName = keyName;
        this.modeName = modeName;
    }

    private String keyName;
    private String modeName;

    public String getModeName() {
        return modeName;
    }

    public String getKeyName() {
        return keyName;
    }

    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private static Map<String, String> initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (PUBGMode s : PUBGMode.values()) {
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
