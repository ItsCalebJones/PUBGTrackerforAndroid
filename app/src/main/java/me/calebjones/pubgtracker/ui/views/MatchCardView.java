package me.calebjones.pubgtracker.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.Participant;
import me.calebjones.pubgtracker.data.models.Stat;
import me.calebjones.pubgtracker.R;


public class MatchCardView extends LinearLayout {

    @BindView(R.id.match_overview_match_title)
    TextView matchOverviewMatchTitle;
    @BindView(R.id.match_overview_date)
    TextView matchOverviewDate;
    @BindView(R.id.match_result)
    TextView matchResult;
    @BindView(R.id.match_rating)
    TextView matchRating;
    @BindView(R.id.match_kills)
    TextView matchKills;
    @BindView(R.id.match_damage)
    TextView matchDamage;
    @BindView(R.id.match_distance)
    TextView matchDistance;
    @BindView(R.id.match_survived)
    TextView matchSurvived;
    @BindView(R.id.match_root)
    LinearLayout matchRoot;
    @BindView(R.id.match_count)
    TextView matchCount;
    @BindView(R.id.match_win)
    TextView matchWin;
    @BindView(R.id.session_container)
    LinearLayout sessionContainer;
    @BindView(R.id.match_result_title)
    TextView matchResultTitle;

    public MatchCardView(Context context) {
        super(context);
        init(context);
    }

    public MatchCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MatchCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.match_card, this);
        ButterKnife.bind(this);
    }

    public void setMatch(Match match, SimpleDateFormat simpleDateFormat) {
        Participant player = match.getRosters().get(0).getParticipant().get(0);
        Stat stats = player.getStats();
        setMatchResult(match);

        int killRating = player.getStats().getKillPoints();
        int winRating = player.getStats().getWinPoints();
        double rating = (winRating + (killRating * 0.2));

        double killRatingDelta = player.getStats().getKillPointsDelta();
        double winRatingDelta = player.getStats().getWinPointsDelta();
        double ratingDelta = (winRatingDelta + (killRating * 0.2));

        String matchRatingText = String.valueOf((int) rating);
        String matchRatingChangeText = String.valueOf((int) ratingDelta);

        if (ratingDelta > 0) {
            matchRatingText = matchRatingText + " (+" + matchRatingChangeText + ")";
        } else {
            matchRatingText = matchRatingText + " (" + matchRatingChangeText + ")";
        }
        String matchKillsText = String.valueOf(match.getGameMode());
        String matchDamageText = String.valueOf(stats.getDamageDealt());
        String matchDistanceText = String.valueOf((int) stats.getRideDistance()) + "m";
        String matchSurvivedText = String.valueOf((int) stats.getTimeSurvived()) + " sec";
        String matchOverviewMatchTitleText = PUBGRegion.findByKey(match.getShardId()) + " - " + match.getGameMode();
        String matchOverviewDateText = simpleDateFormat.format(match.getCreatedAt());


        matchRating.setText(matchRatingText);
        matchKills.setText(matchKillsText);
        matchDamage.setText(matchDamageText);
        matchDistance.setText(matchDistanceText);
        matchSurvived.setText(matchSurvivedText);
        matchOverviewMatchTitle.setText(matchOverviewMatchTitleText);
        matchOverviewDate.setText(matchOverviewDateText);
    }

    private void setMatchResult(Match match) {
        Participant player = match.getRosters().get(0).getParticipant().get(0);
        Stat stats = player.getStats();

        sessionContainer.setVisibility(View.GONE);
        matchResultTitle.setText("RESULT");
        if (stats.getWinPlace() == 1) {
            matchResult.setText("Win");
        } else if (stats.getWinPlace() <= 10) {
            matchResult.setText("Top Ten");
        } else {
            matchResult.setText("Died");
        }

    }
}
