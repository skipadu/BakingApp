package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.model.StepsItem;
import com.pihrit.bakingapp.recyclerviews.RecipeStepAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeStepItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeStepSelectActivity extends AppCompatActivity implements RecipeStepItemClickListener {

    public static final int INGREDIENTS_INDEX = -10;

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRecipeStepsRecyclerView;
    private RecipeStepAdapter mRecipeStepAdapter;

    private Recipe mRecipe;

    private static final String TAG = RecipeStepSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_select);
        ButterKnife.bind(this);

        Log.v(TAG, "onCreate called");

        mRecipeStepsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecipeStepsRecyclerView.setLayoutManager(lm);

//        mRecipeStepsRecyclerView.setNestedScrollingEnabled(false);

        mRecipeStepsRecyclerView.setFocusable(false);

        mRecipeStepAdapter = new RecipeStepAdapter(this, this);
        mRecipeStepsRecyclerView.setAdapter(mRecipeStepAdapter);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            mRecipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);
        }
        // TODO: else, we are coming from the RecipeStepViewActivity
        //
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCELABLE_ID);
        }

        refreshAdapter();
    }

    private void refreshAdapter() {
        Log.v(TAG, "Refresh adapter");
        if (mRecipe != null) {
            Log.v(TAG, "Recipe was not null");
            mRecipeStepAdapter.setRecipeSteps(mRecipe.getSteps());
            mRecipeStepAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecipeStepItemClick(int itemIndex) {
        StepsItem clickedStep = mRecipeStepAdapter.getStepsItemAt(itemIndex);

        Intent recipeStepViewIntent = new Intent(RecipeStepSelectActivity.this, RecipeStepViewActivity.class);
        recipeStepViewIntent.putExtra(StepsItem.PARCELABLE_ID, clickedStep);

        startActivity(recipeStepViewIntent);
    }

    @OnClick(R.id.tv_recipe_step_ingredients)
    public void onIngredientsStepClick() {
        Intent recipeStepViewIntent = new Intent(RecipeStepSelectActivity.this, RecipeStepViewActivity.class);

        recipeStepViewIntent.putParcelableArrayListExtra(IngredientsItem.PARCELABLE_ID, mRecipe.getIngredients());

        startActivity(recipeStepViewIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Recipe.PARCELABLE_ID, mRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable(Recipe.PARCELABLE_ID);
        refreshAdapter();
    }

}
