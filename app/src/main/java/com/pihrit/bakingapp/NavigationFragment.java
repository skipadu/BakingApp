package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Actually navigate to previous & next steps
public class NavigationFragment extends Fragment {

    private int mStepIndex;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    public void setStepIndex(int stepIndex) {
        this.mStepIndex = stepIndex;
    }
}
