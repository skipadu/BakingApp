package com.pihrit.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationFragment extends Fragment {
    private OnNavigationInteractionListener mListener;

    public NavigationFragment() {
        // Required empty public constructor
    }

    public static NavigationFragment newInstance() {
        NavigationFragment fragment = new NavigationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationInteractionListener) {
            mListener = (OnNavigationInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNavigationInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.btn_navigation_previous)
    public void previousButtonClicked() {
        mListener.previousButtonPressed();
    }

    @OnClick(R.id.btn_navigation_next)
    public void nextButtonClicked() {
        mListener.nextButtonPressed();
    }
}
