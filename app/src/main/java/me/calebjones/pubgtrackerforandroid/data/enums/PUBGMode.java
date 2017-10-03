package me.calebjones.pubgtrackerforandroid.data.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PUBGMode {
    ALL("agg", "All"),
    SOLO("solo", "Third Person - Solo"),
    DUO("duo", "Third Person - Duo"),
    SQUAD("squad","Third Person - Squad"),
    FPP_SOLO("solo-fpp","First Person - Solo"),
    FPP_DUO("duo-fpp","First Person - Duo"),
    FPP_SQUAD("squad-fpp","First Person - Squad");

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
