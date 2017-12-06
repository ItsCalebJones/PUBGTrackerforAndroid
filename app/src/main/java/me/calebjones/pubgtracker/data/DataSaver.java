package me.calebjones.pubgtracker.data;

import org.greenrobot.eventbus.EventBus;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.calebjones.pubgtracker.data.events.MatchRefreshing;
import me.calebjones.pubgtracker.data.events.MatchResults;
import me.calebjones.pubgtracker.data.events.UserRefreshing;
import me.calebjones.pubgtracker.data.events.UserSelected;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.models.PlayerStat;
import me.calebjones.pubgtracker.data.models.Stats;
import me.calebjones.pubgtracker.data.models.User;
import timber.log.Timber;


public class DataSaver {

    private Realm realm;

    public DataSaver() {
        realm = Realm.getDefaultInstance();
    }

    public void saveUser(final User user) {
        Timber.i("saveUser - Saving user %s", user.getPlayerName());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User cachedUser = realm.where(User.class).equalTo("pubgTrackerId", user.getPubgTrackerId()).findFirst();
                if (cachedUser != null){
                    if (cachedUser.isFavoriteUser()) {
                        Timber.d("saveUser -executeTransaction - %s is favorite.", user.getPlayerName());
                        user.setFavoriteUser(true);
                    }
                    if (cachedUser.getMatchHistory() != null && cachedUser.getMatchHistory().size() > 0){
                        user.setMatchHistory(cachedUser.getMatchHistory());
                    }
                }

                User currentUser = realm.where(User.class).equalTo("currentUser", true).findFirst();

                if (currentUser != null) {
                    currentUser.setCurrentUser(false);
                    realm.copyToRealmOrUpdate(currentUser);
                }

                for (PlayerStat stat: user.getPlayerStats()){
                    stat.setPrimrayKey();
                    for (Stats stats: stat.getStats()){
                        stats.setId(stat.getPrimaryKey()+stats.getField()+user.getPlayerName());
                    }
                }
                user.setCurrentUser(true);
                realm.copyToRealmOrUpdate(user);
            }
        });
        EventBus.getDefault().post(new UserRefreshing(false));
        EventBus.getDefault().post(new UserSelected(user));
    }

    public void saveMatches(final List<Match> matches, final User user) {
        Timber.i("saveMatches - Saving matches for user %s", user.getPlayerName());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Match match: matches){
                    match.setUser(user);
                }

                for (PlayerStat stat: user.getPlayerStats()){
                    stat.setPrimrayKey();
                    for (Stats stats: stat.getStats()){
                        stats.setId(stat.getPrimaryKey()+stats.getField()+user.getPlayerName());
                    }
                }
                realm.copyToRealmOrUpdate(matches);
                RealmResults<Match> realmList = realm.where(Match.class).equalTo("user.accountId", user.getAccountId()).findAll();
                RealmList<Match> results = new RealmList<Match>();
                results.addAll(realmList.subList(0, realmList.size()));
                user.setMatchHistory(results);
                realm.copyToRealmOrUpdate(user);
            }
        });
        EventBus.getDefault().post(new MatchRefreshing(false));
        EventBus.getDefault().post(new MatchResults(user));
    }

    public void saveStats(final User user) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User cachedUser = realm.where(User.class).equalTo("pubgTrackerId", user.getPubgTrackerId()).findFirst();
                if (cachedUser != null){
                    if (cachedUser.isFavoriteUser()) {
                        Timber.d("saveUser -executeTransaction - %s is favorite.", user.getPlayerName());
                        user.setFavoriteUser(true);
                    }
                    if (cachedUser.getMatchHistory() != null && cachedUser.getMatchHistory().size() > 0){
                        user.setMatchHistory(cachedUser.getMatchHistory());
                    }
                }
                User currentUser = realm.where(User.class).equalTo("currentUser", true).findFirst();

                if (currentUser != null) {
                    currentUser.setCurrentUser(false);
                    realm.copyToRealmOrUpdate(currentUser);
                }
                RealmList<PlayerStat> currentStats = currentUser.getPlayerStats();
                for (PlayerStat stat: user.getPlayerStats()){
                    stat.setPrimrayKey();
                    for (Stats stats: stat.getStats()){
                        stats.setId(stat.getPrimaryKey()+stats.getField()+user.getPlayerName());
                    }
                }
                RealmList<PlayerStat> newStats = user.getPlayerStats();
                newStats.addAll(currentStats);
                user.setPlayerStats(newStats);
                user.setCurrentUser(true);
                realm.copyToRealmOrUpdate(user);
            }
        });
    }
}
