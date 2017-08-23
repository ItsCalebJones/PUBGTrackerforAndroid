package me.calebjones.pubgtrackerforandroid.data.enums;

public enum PUBGMode {
    ALL("All"),
    SOLO("Solo"),
    DUO("Duo"),
    SQUAD("Squad");

    PUBGMode(String modeName) {
        this.modeName = modeName;
    }

    private String modeName;

    public String getModeName() {
        return modeName;
    }
}
