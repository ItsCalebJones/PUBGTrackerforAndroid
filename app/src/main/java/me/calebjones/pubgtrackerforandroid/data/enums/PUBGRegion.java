package me.calebjones.pubgtrackerforandroid.data.enums;

public enum PUBGRegion {
    AAG("All"),
    NA("[NA] North America"),
    AS("[AS] Asia"),
    EU("[EU] Europe");


    PUBGRegion(String modeName) {
        this.modeName = modeName;
    }

    private String modeName;

    public String getRegionName() {
        return modeName;
    }
}
