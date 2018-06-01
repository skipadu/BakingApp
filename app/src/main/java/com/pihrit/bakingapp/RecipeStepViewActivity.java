package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class RecipeStepViewActivity extends AppCompatActivity {
    private StepsItem mStepsItem;
    private ArrayList<IngredientsItem> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(StepsItem.PARCELABLE_ID)) {
            mStepsItem = callerIntent.getExtras().getParcelable(StepsItem.PARCELABLE_ID);
        }
        if (callerIntent.hasExtra(IngredientsItem.PARCELABLE_ID)) {
            mIngredients = callerIntent.getExtras().getParcelableArrayList(IngredientsItem.PARCELABLE_ID);
        }

        // Create new fragments only if no previously saved state
        if (savedInstanceState == null) {
            // TODO: Different layout for the tablet

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (mIngredients == null && mStepsItem != null && (mStepsItem.getVideoURL().length() > 0 || mStepsItem.getThumbnailURL().length() > 0)) {
                MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
                mediaPlayerFragment.setVideoURL(mStepsItem.getVideoURL());
                mediaPlayerFragment.setmThumbnailURL(mStepsItem.getThumbnailURL());

                fragmentManager.beginTransaction()
                        .add(R.id.media_player_container, mediaPlayerFragment)
                        .commit();
            } else {
                View v = findViewById(R.id.media_player_container);
                v.setVisibility(View.GONE);
            }


            StepInstructionsFragment instructionsFragment = new StepInstructionsFragment();
            if (mStepsItem != null) {
                instructionsFragment.setInstructions(mStepsItem.getDescription());
            } else if (mIngredients != null) {
                instructionsFragment.setIngredients(mIngredients);
            }

            fragmentManager.beginTransaction()
                    .add(R.id.step_instructions_container, instructionsFragment)
                    .commit();

            NavigationFragment navigationFragment = new NavigationFragment();
            navigationFragment.setStepIndex(mIngredients != null ? RecipeStepSelectActivity.INGREDIENTS_INDEX : mStepsItem.getId());

            fragmentManager.beginTransaction()
                    .add(R.id.navigation_buttons_container, navigationFragment)
                    .commit();
        }

        if (mStepsItem != null) {
            getSupportActionBar().setTitle(mStepsItem.getShortDescription());
        } else {
            getSupportActionBar().setTitle(getString(R.string.ingredients));
        }

    }

    // Little hack to keep the data still in place when going back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
