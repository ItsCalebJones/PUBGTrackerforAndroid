package me.calebjones.pubgtracker.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.realm.Realm;

public class BaseFragment extends Fragment {
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.removeAllChangeListeners();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public Realm getRealm() {
        return realm;
    }
}
