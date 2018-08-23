package me.calebjones.pubgtracker.ui.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.ButterKnife;
import jonathanfinerty.once.Once;
import me.calebjones.pubgtracker.common.BaseActivity;
import me.calebjones.pubgtracker.data.Config;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.ui.intro.IntroActivity;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private Drawer result = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setTheme(R.style.AppTheme_Transparent);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, Config.SHOW_APP_TOUR)) {
            startActivity(new Intent(this, IntroActivity.class));
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setUpDrawer(savedInstanceState);
    }

    public void setUpDrawer (Bundle savedInstanceState) {
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.playerunknowns_battlegrounds_guy)
                .withAccountHeader(R.layout.material_drawer_header_custom)
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withTranslucentNavigationBar(true)
                .withHasStableIds(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new ExpandableDrawerItem()
                                .withName("Home")
                                .withIcon(GoogleMaterial.Icon.gmd_home)
                                .withIdentifier(R.id.menu_home_master)
                                .withSelectable(false),
                        new PrimaryDrawerItem().withName("Map")
                                .withIcon(GoogleMaterial.Icon.gmd_map)
                                .withIdentifier(R.id.menu_map)
                                .withSelectable(false),
//                        new PrimaryDrawerItem().withName("Compare")
//                                .withIcon(GoogleMaterial.Icon.gmd_compare)
//                                .withIdentifier(R.id.menu_compare)
//                                .withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withIcon(GoogleMaterial.Icon.gmd_info_outline)
                                .withName("What's New?")
                                .withDescription("See the changelog.")
                                .withIdentifier(R.id.menu_new)
                                .withSelectable(false),
                        new SecondaryDrawerItem()
                                .withIcon(GoogleMaterial.Icon.gmd_feedback)
                                .withName("Feedback")
                                .withDescription("Found a bug?")
                                .withIdentifier(R.id.menu_feedback)
                                .withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch ((int) drawerItem.getIdentifier()) {
                                case R.id.menu_home_master:
                                    break;
                                case R.id.menu_new:
                                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                                            .title("Whats New?")
                                            .content("NOTHING - It's a Beta!")
                                            .positiveText("Got it!");
                                    builder.show();
                                    break;
                                case R.id.menu_feedback:
//                                    createSnackbar("Feedback mechanism coming!");
                                    break;
                                case R.id.menu_settings:
//                                    goToSettings();
                                    break;
                            }
                        }
                        return false;
                    }
                }).build();

//        if (!SupporterHelper.isSupporter()){
        if (true) {
            result.addStickyFooterItem(
                    new PrimaryDrawerItem().withName("Settings")
                            .withIcon(GoogleMaterial.Icon.gmd_settings)
                            .withIdentifier(R.id.menu_settings)
                            .withSelectable(false));
//                    new PrimaryDrawerItem().withName("Become a Supporter")
//                            .withDescription("Get Pro Features")
//                            .withIcon(GoogleMaterial.Icon.gmd_mood)
//                            .withIdentifier(R.id.menu_support)
//                            .withSelectable(false));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.d("onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("onStop");
    }

}
