package me.calebjones.pubgtracker.data.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PUBGRegion {
    /** Asian server for Xbox */
    XBOX_AS("xbox-as", "[Xbox] Asia"),
    /** European server for Xbox */
    XBOX_EU("xbox-eu", "[Xbox] Europe"),
    /** North American server for Xbox */
    XBOX_NA("xbox-na", "[Xbox] North America"),
    /** Oceanic server for Xbox */
    XBOX_OC("xbox-oc", "[Xbox] Oceanic"),
    /** Koran and Japaneses server for PC */
    PC_KRJP("pc-krjp", "[PC] Korea / Japan"),
    /** North American server for PC */
    PC_NA("pc-na", "[PC] North American"),
    /** European server for PC */
    PC_EU("pc-eu", "[PC] Europe"),
    /** Oceanic server for PC */
    PC_OC("pc-oc", "[PC] Oceanic"),
    /** South East Asian server for PC */
    PC_SEA("pc-sea", "[PC] South East Asia"),
    /** South and Central American server for PC */
    PC_SA("pc-sa", "[PC] South Central America"),
    /** Asian server for PC */
    PC_AS("pc-as", "[PC] Asia"),
    /** Spoopy PC server with no name */
    PC_KAKAO("xbox-as", "[PC] Unknown");




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
