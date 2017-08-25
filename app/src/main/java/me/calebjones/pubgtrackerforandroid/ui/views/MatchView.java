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
import timber.log.Timber;


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
        init(context);
    }

    public MatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Timber.v("init - Binding and Inflating views.");
        inflate(context, R.layout.match_layout, this);
        Timber.v("init - View Inflated");
        ButterKnife.bind(this);
        Timber.v("init - Binding Completed.");
    }

    public void setMatch(Match match, SimpleDateFormat simpleDateFormat){
        Timber.v("setMatch - Received Match ID: %s", match.getId());
        setMatchResult(match);

        Timber.v("setMatch - Getting data from Match.");
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
        Timber.v("setMatch - Getting Date from Match.");
        String matchOverviewDateText = simpleDateFormat.format(match.getUpdated());
        Timber.v("setMatch - Getting Date from Match - Complete.");


        Timber.v("setMatch - Setting Views with data.");
        matchRating.setText(matchRatingText);
        matchKills.setText(matchKillsText);
        matchDamage.setText(matchDamageText);
        matchDistance.setText(matchDistanceText);
        matchSurvived.setText(matchSurvivedText);
        matchOverviewMatchTitle.setText(matchOverviewMatchTitleText);
        matchOverviewDate.setText(matchOverviewDateText);
    }

    private void setMatchResult(Match match) {
        Timber.v("setMatchResult - Checking result.");
        if (match.getWins() == 1) {
            Timber.v("setMatchResult - Returning Win.");
            matchResult.setText("Win");
        } else if (match.getTop10() == 1) {
            Timber.v("setMatchResult - Returning Top Ten.");
            matchResult.setText("Top Ten");
        } else {
            Timber.v("setMatchResult - Returning Died.");
            matchResult.setText("Died");
        }
    }


}
