package me.calebjones.pubgtracker.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtracker.data.models.Stat;
import me.calebjones.pubgtracker.R;
import timber.log.Timber;

public class PlaylistView extends CardView {

    @BindView(R.id.playlist_overview_type)
    TextView playlistOverviewType;
    @BindView(R.id.playlist_overview_season)
    TextView playlistOverviewSeason;
    @BindView(R.id.playlist_rating)
    TextView playlistRating;
    @BindView(R.id.playlist_rank)
    TextView playlistRank;
    @BindView(R.id.playlist_matches_played)
    TextView playlistMatchesPlayed;
    @BindView(R.id.playlist_wins)
    TextView playlistWins;
    @BindView(R.id.playlist_top_ten)
    TextView playlistTopTen;
    @BindView(R.id.playlist_top_rank)
    TextView playlistTopRank;
    @BindView(R.id.playlist_kills_avg)
    TextView playlistKillsAvg;
    @BindView(R.id.playlist_damage)
    TextView playlistDamage;
    @BindView(R.id.playlist_kd_ratio)
    TextView playlistKdRatio;
    @BindView(R.id.playlist_kills)
    TextView playlistKills;
    @BindView(R.id.playlist_assists)
    TextView playlistAssists;
    @BindView(R.id.playlist_knock_outs)
    TextView playlistKnockOuts;
    @BindView(R.id.playlist_most_kills)
    TextView playlistMostKills;
    @BindView(R.id.playlist_average_survived)
    TextView playlistAverageSurvived;
    @BindView(R.id.playlist_longest_kill)
    TextView playlistLongestKill;
    @BindView(R.id.playlist_headshot_kills)
    TextView playlistHeadshotKills;
    @BindView(R.id.playlist_vehicles_destroyed)
    TextView playlistVehiclesDestroyed;
    @BindView(R.id.playlist_vehicle_kills)
    TextView playlistVehicleKills;
    @BindView(R.id.playlist_heals)
    TextView playlistHeals;
    @BindView(R.id.playlist_revives)
    TextView playlistRevives;
    @BindView(R.id.playlist_boosts)
    TextView playlistBoosts;
    @BindView(R.id.playlist_suicides)
    TextView playlistSuicides;
    @BindView(R.id.playlist_team_kills)
    TextView playlistTeamKills;
    @BindView(R.id.playlist_root)
    LinearLayout playlistRoot;
    @BindView(R.id.playlist_win_rate)
    TextView playlistWinRate;
    @BindView(R.id.playlist_top_ten_rate)
    TextView playlistTopTenRate;
    @BindView(R.id.overview_card)
    CardView overviewCard;
    @BindView(R.id.expand_button)
    View expandButton;
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    public PlaylistView(Context context) {
        super(context);
        init(context);
    }

    public PlaylistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlaylistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Timber.v("init - Binding and Inflating views.");
        inflate(context, R.layout.playlist_overview_card_full, this);
        Timber.v("init - View Inflated");
        ButterKnife.bind(this);
        Timber.v("init - Binding Completed.");
    }

    public int getVisible(){
        return this.getVisibility();
    }

    public View getExpandButtonTarget(){
        return expandButton;
    }

    public void setStats(Stat stat) {
    }

    @OnClick(R.id.expand_button)
    public void onViewClicked() {
        expandableLayout.toggle(true);
    }
}
