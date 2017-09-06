package me.calebjones.pubgtrackerforandroid.ui.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import me.calebjones.pubgtrackerforandroid.R;
import me.calebjones.pubgtrackerforandroid.data.models.Match;
import me.calebjones.pubgtrackerforandroid.ui.views.MatchView;
import timber.log.Timber;


public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {

    private RealmResults<Match> matches;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm zzz - EEEE, MMMM dd, yyyy ", Locale.US);
    private Context context;

    public HistoryRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setMatches(RealmResults<Match> newMatches) {
        if (newMatches == null || newMatches.size() == 0) {
            return;
        }
        matches = newMatches;
        notifyDataSetChanged();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.match_view_adapter, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.matchView.setMatch(match, simpleDateFormat);
    }

    @Override
    public int getItemCount() {
        if (matches != null) {
            return matches.size();
        } else {
            return 0;
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.match_view)
        public MatchView matchView;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
