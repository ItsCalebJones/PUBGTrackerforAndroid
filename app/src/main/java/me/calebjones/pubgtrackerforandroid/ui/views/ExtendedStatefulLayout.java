package me.calebjones.pubgtrackerforandroid.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import java.text.SimpleDateFormat;

import cz.kinst.jakub.view.SimpleStatefulLayout;
import cz.kinst.jakub.view.StatefulLayout;
import me.calebjones.pubgtrackerforandroid.R;

public class ExtendedStatefulLayout extends SimpleStatefulLayout {
    public ExtendedStatefulLayout(Context context) {
        super(context);
        init();
    }

    public ExtendedStatefulLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtendedStatefulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setStateView(State.NO_USER, LayoutInflater.from(getContext()).inflate(R.layout.no_user_layout, null));
        setStateView(State.EMPTY, LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null));
        setStateView(State.ERROR, LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null));
    }

    public class State extends SimpleStatefulLayout.State {
        // Note: CONTENT state is inherited from parent
        public static final String NO_USER = "no_user";
        public static final String ERROR = "error";
    }

    public void showNoUser() {
        setState(State.NO_USER);
    }

    public void showError() {
        setState(State.ERROR);
    }
}
