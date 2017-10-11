package me.calebjones.pubgtracker.ui.settings.about;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.ui.intro.IntroActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        loadAbout();
    }

    private void loadAbout() {
        final FrameLayout flHolder = (FrameLayout) this.findViewById(R.id.about);

        AboutBuilder builder = AboutBuilder.with(this)
                .setAppIcon(R.mipmap.ic_launcher_foreground)
                .setAppName(R.string.app_name)
                .setPhoto(R.drawable.ic_jones_logo)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(10)
                .setName("Caleb Jones")
                .setSubTitle("Amateur Imagineer")
                .setLinksColumnsCount(3)
                .setBrief("Gamer, Nerd, Developer, Tester.")
                .addGooglePlayStoreLink("7111684947714289915")
                .addGitHubLink("itscalebjones")
                .addTwitterLink("koun7erfit")
                .setVersionNameAsAppSubTitle()
                .addShareAction("Checkout " + R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addIntroduceAction(new Intent(this, IntroActivity.class))
                .addDonateAction((Intent) null)
                .setWrapScrollView(true)
                .setShowAsCard(true);


        AboutView view = builder.build();

        flHolder.addView(view);
    }
}
