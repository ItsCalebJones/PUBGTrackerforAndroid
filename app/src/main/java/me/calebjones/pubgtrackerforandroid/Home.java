package me.calebjones.pubgtrackerforandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.calebjones.pubgtrackerforandroid.data.models.APIResponse;
import me.calebjones.pubgtrackerforandroid.data.networking.DataClient;
import me.calebjones.pubgtrackerforandroid.data.networking.response.TrackerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Home extends AppCompatActivity {

    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.button)
    AppCompatButton button;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_home_test:
                    mTextMessage.setText("Test");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient.getInstance().getProfileByName("Kon7raband", new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful()){
                            APIResponse apiResponse = response.body();
                            if (apiResponse != null) {
                                if (apiResponse.getError() != null) {
                                    mTextMessage.setText(apiResponse.getError());
                                } else if (apiResponse.getPlayerName() != null) {
                                    mTextMessage.setText(apiResponse.getPlayerName());
                                }
                            }
                        } else {
                            Timber.e(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Timber.e(t);
                    }
                });
            }
        });
    }

}
