package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pihrit.bakingapp.model.StepsItem;

public class RecipeStepViewActivity extends AppCompatActivity {
    private StepsItem mStepsItem;
    private boolean mIsIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(StepsItem.PARCELABLE_ID)) {
            mStepsItem = callerIntent.getExtras().getParcelable(StepsItem.PARCELABLE_ID);
        }
        if (callerIntent.hasExtra(RecipeStepSelectActivity.INGREDIENTS_EXTRA)) {
            mIsIngredients = callerIntent.getExtras().getBoolean(RecipeStepSelectActivity.INGREDIENTS_EXTRA);
        }

        // Create new fragments only if no previously saved state
        if (savedInstanceState == null) {
            // TODO: Different layout for the tablet

            FragmentManager fragmentManager = getSupportFragmentManager();

            MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
            mediaPlayerFragment.setVideoURL(mStepsItem.getVideoURL());
            mediaPlayerFragment.setmThumbnailURL(mStepsItem.getThumbnailURL());

            fragmentManager.beginTransaction()
                    .add(R.id.media_player_container, mediaPlayerFragment)
                    .commit();

            StepInstructionsFragment instructionsFragment = new StepInstructionsFragment();
            instructionsFragment.setInstructions(mStepsItem.getDescription());

            fragmentManager.beginTransaction()
                    .add(R.id.step_instructions_container, instructionsFragment)
                    .commit();

            NavigationFragment navigationFragment = new NavigationFragment();
            navigationFragment.setStepIndex(mIsIngredients ? RecipeStepSelectActivity.INGREDIENTS_INDEX : mStepsItem.getId());

            fragmentManager.beginTransaction()
                    .add(R.id.navigation_buttons_container, navigationFragment)
                    .commit();
        }
    }

    // Little hack to keep the data still in place when going back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
