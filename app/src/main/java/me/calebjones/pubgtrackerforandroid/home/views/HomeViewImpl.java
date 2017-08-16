package me.calebjones.pubgtrackerforandroid.home.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.calebjones.pubgtrackerforandroid.home.contracts.HomeContract;
import me.calebjones.pubgtrackerforandroid.R;


public class HomeViewImpl implements HomeContract.View {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.profile_name)
    TextView name;
    @BindView(R.id.current_rating)
    TextView rating;
    @BindView(R.id.information_card)
    CardView informationCard;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private HomeContract.Presenter homePresenter;
    private View mRootView;
    private Context context;

    public HomeViewImpl(Context context, LayoutInflater inflater, ViewGroup container){
        this.context = context;
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mRootView);
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
    public void setPresenter(HomeContract.Presenter presenter) {
        homePresenter = presenter;
    }

    @Override
    public void setProfileAvatar(String name) {
        Glide.with(context)
                .load(name)
                .into(avatar);
    }

    @Override
    public void setProfileName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setCurrentRating(String rating) {
        String message = context.getString(R.string.current_rating) + rating;
        this.rating.setText(message);
    }

    @Override
    public void showInformationCard() {
        informationCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInformationCard() {
        informationCard.setVisibility(View.GONE);
    }

    @Override
    public void showIntroHelper() {

    }

    @OnClick(R.id.exploreButton)
    @Override
    public void onInformationCardDismissClicked() {
        hideInformationCard();
        homePresenter.setInformationCardDismissed(true);
    }

    @Override
    public void setRefreshEnabled(boolean state) {
        refresh.setEnabled(state);
    }
}
