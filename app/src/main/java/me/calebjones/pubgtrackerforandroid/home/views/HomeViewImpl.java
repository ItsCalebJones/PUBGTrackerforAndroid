package me.calebjones.pubgtrackerforandroid.home.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.transitionseverywhere.TransitionManager;

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
    @BindView(R.id.overview_card)
    CardView overviewCard;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.root)
    ViewGroup container;


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
    public void setInformationCardVisible(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        informationCard.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOverviewCardVisible(boolean state) {
        TransitionManager.beginDelayedTransition(container);
        overviewCard.setVisibility(state ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showIntroHelper() {

    }

    @OnClick(R.id.exploreButton)
    @Override
    public void onInformationCardDismissClicked() {
        setInformationCardVisible(false);
//        homePresenter.setInformationCardDismissed(true);
    }

    @Override
    public void setRefreshEnabled(boolean state) {
        refresh.setEnabled(state);
    }
}
