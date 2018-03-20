package me.calebjones.pubgtracker.data.models;

import com.github.jasminb.jsonapi.ResourceIdHandler;

/**
 * Created by ALPCJONESM2 on 3/19/18.
 */

public class CustomIdHandler implements ResourceIdHandler {

    /**
     * Creates new CustomIdHandler.
     *
     */
    public CustomIdHandler() {
        // Default constructor
    }

    @Override
    public String asString(Object identifier) {
        if (identifier != null) {
            return String.valueOf(identifier);
        }
        return null;
    }

    @Override
    public String fromString(String source) {
        return source;
    }
}
