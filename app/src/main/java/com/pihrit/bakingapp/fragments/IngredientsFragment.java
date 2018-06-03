package com.pihrit.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.activities.DetailsViewActivity;
import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.recyclerviews.IngredientsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsFragment extends Fragment {
    public static final String TAG = IngredientsFragment.class.getSimpleName();
    private static final String BUNDLE_INGREDIENTS = "ingredients";

    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsRecyclerView;

    private IngredientsAdapter mIngredientsAdapter;
    private ArrayList<IngredientsItem> mIngredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(ArrayList<IngredientsItem> ingredients) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(DetailsViewActivity.ARGUMENT_INGREDIENTS, ingredients);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIngredients = getArguments().getParcelableArrayList(DetailsViewActivity.ARGUMENT_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mIngredients = savedInstanceState.getParcelableArrayList(BUNDLE_INGREDIENTS);
        }

        mIngredientsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mIngredientsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsAdapter = new IngredientsAdapter(getActivity());
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);

        mIngredientsAdapter.setIngredients(mIngredients);
        mIngredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_INGREDIENTS, mIngredients);
    }
}
