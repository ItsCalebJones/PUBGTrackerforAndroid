package me.calebjones.pubgtrackerforandroid.data.events;


import me.calebjones.pubgtrackerforandroid.data.models.User;

public class UserSelected {

    public final User response;

    public UserSelected(User response){
        this.response = response;
    }
}
