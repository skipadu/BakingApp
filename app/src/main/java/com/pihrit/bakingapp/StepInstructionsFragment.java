package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pihrit.bakingapp.model.IngredientsItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepInstructionsFragment extends Fragment {

    @BindView(R.id.tv_frag_instructions_step)
    public TextView mInstructionsTextView;

    private String mInstructions;
    private ArrayList<IngredientsItem> mIngredients;

    public StepInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_instructions, container, false);
        ButterKnife.bind(this, v);

        if (mInstructions != null) {
            mInstructionsTextView.setText(mInstructions);
        } else if (mIngredients != null) {
            for (IngredientsItem item : mIngredients) {
                // TODO: create some elements to show all ingredients nicely. Cards?
            }

            //mInstructionsTextView.setText();
        }


        return v;
    }

    public void setInstructions(String instructions) {
        this.mInstructions = instructions;
    }

    public void setIngredients(ArrayList<IngredientsItem> ingredients) {
        this.mIngredients = ingredients;
    }
}
