package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.recyclerviews.IngredientsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepInstructionsFragment extends Fragment {

    private static final String EXTRA_INSTRUCTIONS = "instructions";
    @BindView(R.id.rv_ingredients)
    public RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.tv_frag_instructions_step)
    public TextView mInstructionsTextView;

    private String mInstructions;
    private ArrayList<IngredientsItem> mIngredients;
    private IngredientsAdapter mIngredientsAdapter;

    public StepInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_instructions, container, false);
        ButterKnife.bind(this, v);

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(IngredientsItem.PARCELABLE_ID);
            mInstructions = savedInstanceState.getString(EXTRA_INSTRUCTIONS);
        }

        mIngredientsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mIngredientsRecyclerView.setLayoutManager(lm);
        mIngredientsAdapter = new IngredientsAdapter(getActivity());
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);

        if (mInstructions != null) {
            mInstructionsTextView.setText(mInstructions);
        } else {
            mInstructionsTextView.setVisibility(View.GONE);
        }
        if (mIngredients != null) {
            mIngredientsAdapter.setIngredients(mIngredients);
        }

        return v;
    }

    public void setInstructions(String instructions) {
        this.mInstructions = instructions;
    }

    public void setIngredients(ArrayList<IngredientsItem> ingredients) {
        this.mIngredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(IngredientsItem.PARCELABLE_ID, mIngredients);
        outState.putString(EXTRA_INSTRUCTIONS, mInstructions);
    }
}
