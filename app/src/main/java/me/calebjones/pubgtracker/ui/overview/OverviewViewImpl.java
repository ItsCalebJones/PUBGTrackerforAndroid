package me.calebjones.pubgtracker.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.ui.views.ExtendedStatefulLayout;
import me.calebjones.pubgtracker.ui.views.MatchView;
import me.calebjones.pubgtracker.R;
import me.calebjones.pubgtracker.data.enums.PUBGSeason;
import me.calebjones.pubgtracker.data.models.PlayerStat;


public class OverviewViewImpl implements OverviewContract.View {

    private static final String STATE_EMPTY = "STATE_EMPTY";
    private static final String STATE_NO_USER = "NO_USER";
    @BindView(R.id.information_card)
    CardView informationCard;
    @BindView(R.id.overview_card)
    CardView overviewCard;
    @BindView(R.id.root)
    ViewGroup container;
    @BindView(R.id.close_information_button)
    AppCompatButton closeButton;
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
    @BindView(R.id.overview_stat_one_root)
    LinearLayout overviewStatOneRoot;
    @BindView(R.id.overview_stat_two_root)
    LinearLayout overviewStatTwoRoot;
    @BindView(R.id.home_coordinator)
    CoordinatorLayout homeCoordinator;
    @BindView(R.id.last_match_card)
    CardView matchCard;
    @BindView(R.id.last_match_overview_subheading)
    TextView lastMatchOverviewSubheading;
    @BindView(R.id.match_stat_one)
    TextView matchStatOne;
    @BindView(R.id.match_view)
    MatchView matchView;
    @BindView(R.id.show_intro_button)
    AppCompatButton showIntroButton;
    @BindView(R.id.overview_state_view)
    ExtendedStatefulLayout overviewStateView;

    private OverviewContract.Presenter overviewPresenter;
    private View mRootView;
    private Context context;

    public OverviewViewImpl(Context context, LayoutInflater inflater, ViewGroup container) {
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, mRootView);
        overviewStateView.setStateView(STATE_EMPTY, LayoutInflater.from(context).inflate(R.layout.empty_layout, null));
        overviewStateView.setStateView(STATE_NO_USER, LayoutInflater.from(context).inflate(R.layout.no_user_layout, null));
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
    public void setPresenter(OverviewContract.Presenter presenter) {
        overviewPresenter = presenter;
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
    public void setMatchCardContent(Match match) {
        TransitionManager.beginDelayedTransition(container);
        matchView.setMatch(match, new SimpleDateFormat("HH:mm zzz - EEEE, MMMM dd, yyyy ",
                Locale.US));
    }

    @Override
    public void setOverviewContent(PlayerStat playerStat) {
        TransitionManager.beginDelayedTransition(container);
        playlistOneRoot.setVisibility(View.VISIBLE);
        playlistOverviewSeason.setText(PUBGSeason.findByKey(playerStat.getSeason()));
        String match = playerStat.getMatch();
        if (match != null) {
            match = match.substring(0, 1).toUpperCase() + match.substring(1).toLowerCase();
        } else {
            match = "Unknown";
        }
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
    public void createSnackbar(String message) {
        Snackbar.make(homeCoordinator, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmpty() {
        overviewStateView.showEmpty();
    }

    @Override
    public void showContent() {
        overviewStateView.showContent();
    }

    @Override
    public void showNoUser() {
        overviewStateView.showNoUser();
    }


    @Override
    public void showIntroHelper() {

    }

    @OnClick(R.id.close_information_button)
    @Override
    public void onInformationCardDismissClicked() {
        setInformationCardVisible(false);
        overviewPresenter.setInformationCardDismissed(true);
    }

    @OnClick(R.id.show_intro_button)
    public void onShowIntro() {
        overviewPresenter.showIntro();
    }

}
