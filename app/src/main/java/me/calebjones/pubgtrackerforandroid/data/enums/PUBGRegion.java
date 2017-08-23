package me.calebjones.pubgtrackerforandroid.data.enums;

public enum PUBGRegion {
    AAG("All"),
    NA("North America"),
    AS("Asia"),
    EU("Europe");


    PUBGRegion(String modeName) {
        this.modeName = modeName;
    }

    private String modeName;

    public String getRegionName() {
        return modeName;
    }
}
