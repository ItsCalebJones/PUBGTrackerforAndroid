package me.calebjones.pubgtrackerforandroid.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.List;

import jonathanfinerty.once.Once;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.Config;
import me.calebjones.pubgtrackerforandroid.ui.main.MainActivity;

/**
 * Created by ALPCJONESM2 on 8/30/17.
 */

public class IntroActivity extends AhoyOnboarderActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AhoyOnboarderCard statsCard = new AhoyOnboarderCard("Stats",
                "Track your kills, wins and ranking across multiple seasons.",
                R.drawable.ic_account_circle_48);
        AhoyOnboarderCard compareCard = new AhoyOnboarderCard("Compare",
                "Easily compare your stats to your friends and favorite streamers.",
                R.drawable.ic_thumbs_up_down);
        AhoyOnboarderCard planCard = new AhoyOnboarderCard("Plan",
                "Plan your strategies and improve your game.",
                R.drawable.ic_satellite);

        statsCard.setBackgroundColor(R.color.colorPrimary);
        compareCard.setBackgroundColor(R.color.colorAccent);
        planCard.setBackgroundColor(R.color.colorAccentAlt);


        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(statsCard);
        pages.add(compareCard);
        pages.add(planCard);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.material_typography_primary_text_color_light);
            page.setDescriptionColor(R.color.material_typography_secondary_text_color_light);
        }

        setFinishButtonTitle("Done");
        showNavigationControls(false);

        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.colorPrimaryDark);
        colorList.add(R.color.colorAccentDark);
        colorList.add(R.color.colorAccentAltDark);

        setColorBackground(colorList);
        setImageBackground(R.drawable.playerunknowns_battlegrounds_guy);
        setOnboardPages(pages);
    }
    @Override
    public void onFinishButtonPressed() {
        Once.markDone(Config.SHOW_APP_TOUR);
        startActivity(new Intent(this, MainActivity.class));
    }
}
