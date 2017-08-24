package me.calebjones.pubgtrackerforandroid.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.models.Match;


public class MatchView extends LinearLayout {

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

    public MatchView(Context context) {
        super(context);
        init();
    }

    public MatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.match_layout, this);
        ButterKnife.bind(this);
    }

    public void setMatch(Match match){
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
        String matchDistanceText = String.valueOf((int) match.getMoveDistance()) + "km";
        String matchSurvivedText = String.valueOf((int) match.getTimeSurvived()) + " sec";
        String matchOverviewMatchTitleText = match.getRegionDisplay() + " - " + match.getMatchDisplay();
        String matchOverviewDateText = new SimpleDateFormat(" HH:mm zzz | EEEE, MMMM dd, yyyy ",
                Locale.US).format(match.getUpdated());


        matchRating.setText(matchRatingText);
        matchKills.setText(matchKillsText);
        matchDamage.setText(matchDamageText);
        matchDistance.setText(matchDistanceText);
        matchSurvived.setText(matchSurvivedText);
        matchOverviewMatchTitle.setText(matchOverviewMatchTitleText);
        matchOverviewDate.setText(matchOverviewDateText);
    }

    private void setMatchResult(Match match) {
        if (match.getWins() == 1) {
            matchResult.setText("Win");
        } else if (match.getTop10() == 1) {
            matchResult.setText("Top Ten");
        } else {
            matchResult.setText("Died");
        }
    }


}
