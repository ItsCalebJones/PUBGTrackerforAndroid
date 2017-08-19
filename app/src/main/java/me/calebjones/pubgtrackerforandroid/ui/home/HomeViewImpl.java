package me.calebjones.pubgtrackerforandroid.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;


public class HomeViewImpl implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.profile_name)
    TextView name;
    @BindView(R.id.current_rank)
    TextView rank;
    @BindView(R.id.information_card)
    CardView informationCard;
    @BindView(R.id.overview_card)
    CardView overviewCard;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.root)
    ViewGroup container;
    @BindView(R.id.exploreButton)
    AppCompatButton exploreButton;
    @BindView(R.id.overview_stat_one)
    TextView overviewStatOne;
    @BindView(R.id.overview_stat_one_ranking)
    TextView overviewStatOneRanking;
    @BindView(R.id.overview_stat_two)
    TextView overviewStatTwo;
    @BindView(R.id.overview_stat_two_ranking)
    TextView overviewStatTwoRanking;
    @BindView(R.id.playlist_overview_match)
    TextView playlistOverviewMatch;
    @BindView(R.id.playlist_overview_season)
    TextView playlistOverviewSeason;
    @BindView(R.id.playlist_rating)
    TextView playlistRating;
    @BindView(R.id.playlist_rating_rank)
    TextView playlistRatingRank;
    @BindView(R.id.playlist_win_rate)
    TextView playlistWinRate;
    @BindView(R.id.playlist_win_rate_ranking)
    TextView playlistWinRateRanking;
    @BindView(R.id.playlist_top_ten_rate)
    TextView playlistTopTenRate;
    @BindView(R.id.playlist_top_ten_ranking)
    TextView playlistTopTenRanking;
    @BindView(R.id.playlist_KD_rate)
    TextView playlistKDRate;
    @BindView(R.id.playlist_KD_ranking)
    TextView playlistKDRanking;
    @BindView(R.id.playlist_root)
    LinearLayout playlistOneRoot;
    @BindView(R.id.current_KD)
    TextView currentKD;
    @BindView(R.id.overview_stat_one_root)
    LinearLayout overviewStatOneRoot;
    @BindView(R.id.overview_stat_two_root)
    LinearLayout overviewStatTwoRoot;
    @BindView(R.id.default_user_status_icon)
    IconicsImageView defaultUserStatusIcon;
    @BindView(R.id.home_coordinator)
    CoordinatorLayout homeCoordinator;
    @BindView(R.id.avatar_group)
    LinearLayout avatarGroup;
    @BindView(R.id.last_match_card)
    CardView matchCard;


    private HomeContract.Presenter homePresenter;
    private View mRootView;
    private Context context;
    private boolean defaultUserState;

    public HomeViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mRootView);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        homePresenter = presenter;
    }

    @Override
    public void setProfileAvatar(String name) {
        Glide.with(context)
                .load(name)
                .into(avatar);
    }

    @Override
    public void setProfileName(String name) {
        TransitionManager.beginDelayedTransition(container);
        avatarGroup.setVisibility(View.VISIBLE);
        this.name.setText(name);
    }

    @Override
    public void setCurrentRatingAndRank(String rating, String rank, String kd) {
        TransitionManager.beginDelayedTransition(container);
        String stringRank = "#" + rank;
        String stringKd = context.getString(R.string.current_Kd) + " " + kd;
        this.rank.setText(stringRank);
        this.currentKD.setText(stringKd);
        this.currentKD.setVisibility(View.VISIBLE);
        this.rank.setVisibility(View.VISIBLE);
    }

    @Override
    public void setInformationCardVisible(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        informationCard.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOverviewCardVisible(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        overviewCard.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setMatchCardVisible(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        matchCard.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOverviewSeasonOne(PlayerStat playerStat) {
        TransitionManager.beginDelayedTransition(container);
        playlistOneRoot.setVisibility(View.VISIBLE);
        playlistOverviewSeason.setText(PUBGSeason.findByKey(playerStat.getSeason()));
        String match = playerStat.getMatch();
        match = match.substring(0, 1).toUpperCase() + match.substring(1).toLowerCase();
        playlistOverviewMatch.setText(playerStat.getRegion().toUpperCase() + " - " + match);

        overviewStatOneRoot.setVisibility(View.VISIBLE);
        overviewStatOne.setText(playerStat.getRegion().toUpperCase() + " - " + match);
        String rank = "#" + String.valueOf(playerStat.getStats().get(9).getRank());
        overviewStatOneRanking.setText(rank);

        playlistRating.setText(playerStat.getStats().get(9).getDisplayValue());
        if (playerStat.getStats().get(9).getRank() == 0) {
            playlistRatingRank.setText("");
        } else {
            playlistRatingRank.setText(context.getString(R.string.rank,
                    String.valueOf(playerStat.getStats().get(9).getRank())));
        }

        playlistWinRate.setText(playerStat.getStats().get(1).getDisplayValue());
        if (playerStat.getStats().get(1).getRank() == 0) {
            playlistWinRateRanking.setText("");
        } else {
            playlistWinRateRanking.setText(context.getString(R.string.rank,
                    String.valueOf(playerStat.getStats().get(1).getRank())));
        }

        playlistTopTenRate.setText(playerStat.getStats().get(7).getDisplayValue());
        if (playerStat.getStats().get(7).getRank() == 0) {
            playlistTopTenRanking.setText("");
        } else {
            playlistTopTenRanking.setText(context.getString(R.string.rank,
                    String.valueOf(playerStat.getStats().get(7).getRank())));
        }

        playlistKDRate.setText(playerStat.getStats().get(0).getDisplayValue());
        if (playerStat.getStats().get(0).getRank() == 0) {
            playlistKDRanking.setText("");
        } else {
            playlistKDRanking.setText(context.getString(R.string.rank,
                    String.valueOf(playerStat.getStats().get(0).getRank())));
        }

    }

    @Override
    public void setOverviewSeasonTwo(PlayerStat playerStat) {
        TransitionManager.beginDelayedTransition(container);
        String match = playerStat.getMatch();
        match = match.substring(0, 1).toUpperCase() + match.substring(1).toLowerCase();
        overviewStatTwoRoot.setVisibility(View.VISIBLE);
        overviewStatTwo.setText(playerStat.getRegion().toUpperCase() + " - " + match);
        String rank = "#" + String.valueOf(playerStat.getStats().get(9).getRank());
        overviewStatTwoRanking.setText(rank);
    }

    @Override
    public void setOverviewSeasonOneVisible(boolean state) {
        playlistOneRoot.setVisibility(View.VISIBLE);
        overviewStatOneRoot.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOverviewSeasonTwoVisible(boolean state) {
        overviewStatTwoRoot.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setDefaultUserIcon(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        defaultUserState = state;
        if (defaultUserState) {
            defaultUserStatusIcon.setIcon(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_favorite)
                    .color(ContextCompat.getColor(context, R.color.colorAccentAlt))
                    .sizeDp(16));
        } else {
            defaultUserStatusIcon.setIcon(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_favorite_border)
                    .color(ContextCompat.getColor(context, R.color.material_color_white))
                    .sizeDp(16));
        }
    }

    @Override
    public void createSnackbar(String message) {
        Snackbar.make(homeCoordinator, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setRefreshing(boolean state) {
        refresh.setRefreshing(state);
    }


    @Override
    public void showIntroHelper() {

    }

    @OnClick(R.id.exploreButton)
    @Override
    public void onInformationCardDismissClicked() {
        setInformationCardVisible(false);
        homePresenter.setInformationCardDismissed(true);
    }

    @Override
    public void setRefreshEnabled(boolean state) {
        refresh.setEnabled(state);
    }

    @OnClick(R.id.exploreButton)
    public void onExploreViewClicked() {
    }

    @OnClick(R.id.default_user_status_icon)
    public void onDefaultUserIconClicked() {
        defaultUserState = !defaultUserState;
        setDefaultUserIcon(defaultUserState);
        homePresenter.setDefaultUserState(defaultUserState);
    }

    @Override
    public void onRefresh() {
        homePresenter.refreshCurrentUser();
    }
}
