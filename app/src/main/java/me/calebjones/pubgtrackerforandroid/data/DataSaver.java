package me.calebjones.pubgtrackerforandroid.data;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import me.calebjones.pubgtrackerforandroid.data.events.UserSelected;
import me.calebjones.pubgtrackerforandroid.data.models.User;



public class DataSaver {

    private Realm realm;

    public DataSaver() {
        realm = Realm.getDefaultInstance();
    }

    public void save(final User user) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
        EventBus.getDefault().post(new UserSelected(user));
    }
}