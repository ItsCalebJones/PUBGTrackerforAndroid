package me.calebjones.pubgtrackerforandroid.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.models.Match;


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
        setMatchResult(match);

        String matchRatingText = String.valueOf((int) match.getRating());
        String matchRatingChangeText = String.valueOf((int) match.getRatingChange());

        if (match.getRatingChange() > 0) {
            matchRatingText = matchRatingText + " (+" + matchRatingChangeText + ")";
        } else {
            matchRatingText = matchRatingText + " (" + matchRatingChangeText + ")";
        }
        String matchKillsText = String.valueOf(match.getKills());
        String matchDamageText = String.valueOf(match.getDamage());
        String matchDistanceText = String.valueOf((int) match.getMoveDistance()) + "m";
        String matchSurvivedText = String.valueOf((int) match.getTimeSurvived()) + " sec";
        String matchOverviewMatchTitleText = match.getRegionDisplay() + " - " + match.getMatchDisplay();
        String matchOverviewDateText = simpleDateFormat.format(match.getUpdated());


        matchRating.setText(matchRatingText);
        matchKills.setText(matchKillsText);
        matchDamage.setText(matchDamageText);
        matchDistance.setText(matchDistanceText);
        matchSurvived.setText(matchSurvivedText);
        matchOverviewMatchTitle.setText(matchOverviewMatchTitleText);
        matchOverviewDate.setText(matchOverviewDateText);
    }

    private void setMatchResult(Match match) {
        if (match.getRounds() > 1){
            sessionContainer.setVisibility(View.VISIBLE);
            matchCount.setText(String.valueOf(match.getRounds()));
            matchResultTitle.setText("TOP TENS");
            matchResult.setText(String.valueOf(match.getTop10()));
            matchWin.setText(String.valueOf(match.getWins()));
        } else {
            sessionContainer.setVisibility(View.GONE);
            matchResultTitle.setText("RESULT");
            if (match.getWins() == 1) {
                matchResult.setText("Win");
            } else if (match.getTop10() == 1) {
                matchResult.setText("Top Ten");
            } else {
                matchResult.setText("Died");
            }
        }
    }
}
