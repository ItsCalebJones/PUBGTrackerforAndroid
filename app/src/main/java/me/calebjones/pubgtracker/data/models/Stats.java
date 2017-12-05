package me.calebjones.pubgtracker.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Stats extends RealmObject {

    @PrimaryKey
    private String statKey;
    @SerializedName("partition")
    @Expose
    private String partition;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("subLabel")
    @Expose
    private String subLabel;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("ValueInt")
    @Expose
    private int valueInt;
    @SerializedName("valueDec")
    @Expose
    private float valueDec;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("percentile")
    @Expose
    private float percentile;
    @SerializedName("displayValue")
    @Expose
    private String displayValue;

    private PlayerStat playerStat;

    @LinkingObjects("stats") // <-- !
    private final RealmResults<PlayerStat> stats = null; // <-- !

    public void setPrimrayKey(){
        statKey = playerStat.getPrimaryKey() + label;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public float getValueDec() {
        return valueDec;
    }

    public void setValueDec(float valueDec) {
        this.valueDec = valueDec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getPercentile() {
        return percentile;
    }

    public void setPercentile(float percentile) {
        this.percentile = percentile;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}