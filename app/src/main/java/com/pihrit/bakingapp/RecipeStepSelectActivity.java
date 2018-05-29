package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.model.StepsItem;
import com.pihrit.bakingapp.recyclerviews.RecipeStepAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeStepItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepSelectActivity extends AppCompatActivity implements RecipeStepItemClickListener {
    // TODO: Own item for the ingredients "step"

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRecipeStepsRecyclerView;
    private RecipeStepAdapter mRecipeStepAdapter;

    private ArrayList<StepsItem> mStepItems;

    private static final String TAG = RecipeStepSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_select);
        ButterKnife.bind(this);

        mRecipeStepsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecipeStepsRecyclerView.setLayoutManager(lm);

//        mRecipeStepsRecyclerView.setNestedScrollingEnabled(false);

        mRecipeStepsRecyclerView.setFocusable(false);

        mRecipeStepAdapter = new RecipeStepAdapter(this, this);
        mRecipeStepsRecyclerView.setAdapter(mRecipeStepAdapter);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            Recipe recipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);

            if (recipe != null) {
                mStepItems = recipe.getSteps();
                mRecipeStepAdapter.setRecipeSteps(mStepItems);
                mRecipeStepAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRecipeStepItemClick(int itemIndex) {
        StepsItem clickedStep = mRecipeStepAdapter.getStepsItemAt(itemIndex);

        Log.v(TAG, "Clicked step: id=" + itemIndex + " - " + clickedStep.getDescription());
    }
}
