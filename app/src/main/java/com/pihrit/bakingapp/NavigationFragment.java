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
    private int mLastStepIndex;

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
        int newIndex = mStepIndex + 1;
        if (newIndex > mLastStepIndex) {
            Log.v("ASD", "All steps shown, go back to recipe selection");
        }

        if (getActivity() instanceof RecipeStepViewActivity) {
            ((RecipeStepViewActivity) getActivity()).goToRecipeStepByIndex(newIndex);
        }
    }

    @OnClick(R.id.btn_previous)
    public void goToPreviousIfPossible() {
        int newIndex = mStepIndex - 1;
        if (newIndex <= RecipeStepSelectActivity.INGREDIENTS_INDEX) {
            Log.v("ASD", "Was in ingredients, go back to recipe step selection.");
        }

        if (getActivity() instanceof RecipeStepViewActivity) {
            ((RecipeStepViewActivity) getActivity()).goToRecipeStepByIndex(newIndex);
        }
    }

    public void setLastStepIndex(int lastStepIndex) {
        this.mLastStepIndex = lastStepIndex;
    }
}
