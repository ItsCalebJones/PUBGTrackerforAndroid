package me.calebjones.pubgtrackerforandroid.ui.home;

import android.content.Context;
import android.os.Bundle;
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
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.enums.PUBGSeason;
import me.calebjones.pubgtrackerforandroid.data.models.PlayerStat;


public class HomeViewImpl implements HomeContract.View {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.profile_name)
    TextView name;
    @BindView(R.id.current_rating)
    TextView rating;
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
    @BindView(R.id.season_one_overview_match)
    TextView seasonOneOverviewMatch;
    @BindView(R.id.season_one_overview_season)
    TextView seasonOneOverviewSeason;
    @BindView(R.id.season_one_rating)
    TextView seasonOneRating;
    @BindView(R.id.season_one_rating_rank)
    TextView seasonOneRatingRank;
    @BindView(R.id.season_one_win_rate)
    TextView seasonOneWinRate;
    @BindView(R.id.season_one_win_rate_ranking)
    TextView seasonOneWinRateRanking;
    @BindView(R.id.season_one_top_ten_rate)
    TextView seasonOneTopTenRate;
    @BindView(R.id.season_one_top_ten_ranking)
    TextView seasonOneTopTenRanking;
    @BindView(R.id.season_one_KD_rate)
    TextView seasonOneKDRate;
    @BindView(R.id.season_one_KD_ranking)
    TextView seasonOneKDRanking;
    @BindView(R.id.season_one_root)
    LinearLayout seasonOneRoot;
    @BindView(R.id.season_two_overview_match)
    TextView seasonTwoOverviewMatch;
    @BindView(R.id.season_two_overview_season)
    TextView seasonTwoOverviewSeason;
    @BindView(R.id.season_two_rating)
    TextView seasonTwoRating;
    @BindView(R.id.season_two_ranking)
    TextView seasonTwoRanking;
    @BindView(R.id.season_two_win_rate)
    TextView seasonTwoWinRate;
    @BindView(R.id.season_two_win_ranking)
    TextView seasonTwoWinRanking;
    @BindView(R.id.season_two_top_ten_rate)
    TextView seasonTwoTopTenRate;
    @BindView(R.id.season_two_top_ten_rank)
    TextView seasonTwoTopTenRank;
    @BindView(R.id.season_two_kd_rate)
    TextView seasonTwoKdRate;
    @BindView(R.id.season_two_kd_ranking)
    TextView seasonTwoKdRanking;
    @BindView(R.id.season_two_root)
    LinearLayout seasonTwoRoot;
    @BindView(R.id.current_KD)
    TextView currentKD;


    private HomeContract.Presenter homePresenter;
    private View mRootView;
    private Context context;

    public HomeViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mRootView);
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
        this.name.setText(name);
    }

    @Override
    public void setCurrentRatingAndRank(String rating, String rank, String kd) {
        String stringRating = context.getString(R.string.current_rating) + " " + rating;
        String stringRank = context.getString(R.string.current_rank) + " #" + rank;
        String stringKd = context.getString(R.string.current_Kd) + " " + kd;
        this.rating.setText(stringRating);
        this.rank.setText(stringRank);
        this.currentKD.setText(stringKd);
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
    public void setOverviewSeasonOne(PlayerStat playerStat) {
        TransitionManager.beginDelayedTransition(container);
        seasonOneRoot.setVisibility(View.VISIBLE);
        seasonOneOverviewSeason.setText(PUBGSeason.findByKey(playerStat.getSeason()));
        String match = playerStat.getMatch();
        match = match.substring(0, 1).toUpperCase() + match.substring(1).toLowerCase();
        seasonOneOverviewMatch.setText(playerStat.getRegion().toUpperCase() + " - " + match);

    }

    @Override
    public void setOverviewSeasonTwo(PlayerStat playerStat) {
        TransitionManager.beginDelayedTransition(container);
        seasonTwoRoot.setVisibility(View.VISIBLE);
        seasonTwoOverviewSeason.setText(PUBGSeason.findByKey(playerStat.getSeason()));
        String match = playerStat.getMatch();
        match = match.substring(0, 1).toUpperCase() + match.substring(1).toLowerCase();
        seasonTwoOverviewMatch.setText(playerStat.getRegion().toUpperCase() + " - " + match);
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
    public void onViewClicked() {
    }
}
