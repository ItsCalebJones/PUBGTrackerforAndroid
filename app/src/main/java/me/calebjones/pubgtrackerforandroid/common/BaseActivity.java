package me.calebjones.pubgtrackerforandroid.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    public BaseActivity(){}

    public BaseActivity (String screenName){
        name = screenName;
    }

    private Realm realm;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onStart() {
        super.onStart();
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
