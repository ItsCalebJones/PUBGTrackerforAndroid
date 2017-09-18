package me.calebjones.pubgtrackerforandroid.ui.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MapActivity extends AppCompatActivity {

    private MapViewImpl mapView;
    private MapPresenter mapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = new MapViewImpl(this, null);
        setContentView(mapView.getRootView());
        mapPresenter = new MapPresenter(mapView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
