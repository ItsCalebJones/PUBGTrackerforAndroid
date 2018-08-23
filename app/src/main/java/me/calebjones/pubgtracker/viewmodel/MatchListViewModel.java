package me.calebjones.pubgtracker.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import java.util.List;
import me.calebjones.pubgtracker.data.enums.PUBGRegion;
import me.calebjones.pubgtracker.data.models.Match;
import me.calebjones.pubgtracker.data.networking.DataClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchListViewModel extends ViewModel {

    private MutableLiveData<List<Match>> matches;

    public MatchListViewModel() {
        if (matches == null) {
            matches = new MutableLiveData<List<Match>>();
            loadUsers();
        }
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        DataClient.getInstance().getPlayersMatchesByPlayerName(PUBGRegion.PC_NA.getKeyName(), "Koun7erfit", null, new Callback<JSONAPIDocument<Match[]>>() {
            @Override
            public void onResponse(Call<JSONAPIDocument<Match[]>> call, Response<JSONAPIDocument<Match[]>> response) {

            }

            @Override
            public void onFailure(Call<JSONAPIDocument<Match[]>> call, Throwable t) {

            }
        });
    }
}
