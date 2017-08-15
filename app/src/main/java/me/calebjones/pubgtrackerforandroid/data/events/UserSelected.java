package me.calebjones.pubgtrackerforandroid.data.events;


import me.calebjones.pubgtrackerforandroid.data.models.APIResponse;

public class UserSelected {

    public final APIResponse response;

    public UserSelected(APIResponse response){
        this.response = response;
    }
}
