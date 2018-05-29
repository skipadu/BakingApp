package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.recyclerviews.RecipeStepAdapter;

import butterknife.BindView;

public class RecipeStepSelectActivity extends AppCompatActivity {
    // TODO: Own item for the ingredients "step"

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRecipeStepsRecyclerView;
    private RecipeStepAdapter mRecipeStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_select);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            Recipe recipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);

            if (recipe != null) {
                Log.v("ASD", "TEST: Clicked recipe received");
            }
        }
    }
}
