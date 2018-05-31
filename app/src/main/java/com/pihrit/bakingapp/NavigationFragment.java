package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: Actually navigate to previous & next steps
public class NavigationFragment extends Fragment {
    @BindView(R.id.btn_previous)
    public Button previousButton;

    @BindView(R.id.btn_next)
    public Button nextButton;

    private int mStepIndex;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void setStepIndex(int stepIndex) {
        this.mStepIndex = stepIndex;
    }

    @OnClick(R.id.btn_next)
    public void goToNextIfPossible() {
        Log.v("TEST", "Trying to show next view");
        // TODO: if last, go back to recipe selection?
    }

    @OnClick(R.id.btn_previous)
    public void goToPreviousIfPossible() {
        Log.v("TEST", "Trying to show previous view");
        // TODO: if first, then go to recipe selection?
    }
}
