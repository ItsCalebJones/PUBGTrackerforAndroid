package me.calebjones.pubgtrackerforandroid.common;

import io.realm.Realm;

/**
 * Created by ALPCJONESM2 on 8/15/17.
 */

public class BasePresenter  {

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }
}
