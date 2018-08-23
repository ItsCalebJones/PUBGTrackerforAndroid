package me.calebjones.pubgtracker.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



@JsonIgnoreProperties(ignoreUnknown = true)
@Type("asset")
public class Asset {

    @Id(CustomIdHandler.class)
    @SerializedName("id")
    @Expose
    private String id;
}
