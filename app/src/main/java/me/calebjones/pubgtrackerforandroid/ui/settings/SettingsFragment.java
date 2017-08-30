package me.calebjones.pubgtrackerforandroid.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import me.calebjones.pubgtrackerforandroid.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.general_preferences);
    }
}
