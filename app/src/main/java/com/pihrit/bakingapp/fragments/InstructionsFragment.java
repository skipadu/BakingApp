package com.pihrit.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.activities.DetailsViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsFragment extends Fragment {
    private static final String BUNDLE_INSTRUCTIONS = "instructions";

    @BindView(R.id.tv_frag_instructions_step)
    TextView instructionsTextView;

    private String mInstructions;

    public InstructionsFragment() {
        // Required empty public constructor
    }

    public static InstructionsFragment newInstance(String instructions) {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putString(DetailsViewActivity.ARGUMENT_INSTRUCTIONS, instructions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstructions = getArguments().getString(DetailsViewActivity.ARGUMENT_INSTRUCTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructions, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mInstructions = savedInstanceState.getString(BUNDLE_INSTRUCTIONS);
        }

        instructionsTextView.setText(mInstructions);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_INSTRUCTIONS, mInstructions);
    }
}
