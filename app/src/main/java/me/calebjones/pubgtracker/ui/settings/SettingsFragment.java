package me.calebjones.pubgtracker.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.ui.settings.about.AboutActivity;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getActivity();
        addPreferencesFromResource(R.xml.general_preferences);

        Preference aboutPreference = getPreferenceManager().findPreference("about_me");
        aboutPreference.setIcon(new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_account_box)
                .sizeDp(64)
                .color(Color.WHITE));
        aboutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(context, AboutActivity.class));
                return true;
            }
        });

        Preference pubgPreference = getPreferenceManager().findPreference("pubg_tracker");
        pubgPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String url = "https://pubgtracker.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }
        });
    }
}
