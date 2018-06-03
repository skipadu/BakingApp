package com.pihrit.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.activities.DetailsActivity;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.recyclerviews.RecipeStepAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeStepItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepsFragment extends Fragment implements RecipeStepItemClickListener {

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mStepsRecyclerView;
    private RecipeStepAdapter mStepAdapter;

    private OnStepInteractionListener mListener;
    private Recipe mRecipe;

    public StepsFragment() {
        // Required empty public constructor
    }

    public static StepsFragment newInstance(Recipe recipe) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailsActivity.ARGUMENT_RECIPE, recipe);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(DetailsActivity.ARGUMENT_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, v);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCELABLE_ID);
        }

        mStepsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mStepsRecyclerView.setLayoutManager(lm);
        mStepsRecyclerView.setFocusable(false);

        mStepAdapter = new RecipeStepAdapter(getActivity(), this);
        mStepsRecyclerView.setAdapter(mStepAdapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepInteractionListener) {
            mListener = (OnStepInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Recipe.PARCELABLE_ID, mRecipe);
    }

    @Override
    public void onRecipeStepItemClick(int itemIndex) {
        if (mListener != null) {
            mListener.recipeClicked(itemIndex);
        }
    }

    @OnClick(R.id.tv_step_ingredients)
    public void ingredientsClicked() {
        if (mListener != null) {
            mListener.ingredientsClicked();
        }
    }
}
