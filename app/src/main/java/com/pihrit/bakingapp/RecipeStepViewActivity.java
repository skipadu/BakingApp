package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pihrit.bakingapp.fragments.NavigationFragment;
import com.pihrit.bakingapp.fragments.OnNavigationInteractionListener;
import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class RecipeStepViewActivity extends AppCompatActivity implements OnNavigationInteractionListener {

    public static final String ARGUMENT_VIDEO_URL = "video-url";

    private StepsItem mStepsItem;
    private ArrayList<IngredientsItem> mIngredients;
    private Recipe mRecipe;
    private int mSelectedRecipeStepIndex;

    private static final String TAG = RecipeStepViewActivity.class.getSimpleName();
    private static final String SELECTED_STEP_INDEX = "current-step-index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        Intent callerIntent = getIntent();

        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            mRecipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);
            mSelectedRecipeStepIndex = callerIntent.getExtras().getInt(RecipeStepSelectActivity.EXTRA_RECIPE_STEP_INDEX);
            if (mSelectedRecipeStepIndex >= 0) {
                mStepsItem = mRecipe.getSteps().get(mSelectedRecipeStepIndex);
            }
            mIngredients = mRecipe.getIngredients();
        }
        if (savedInstanceState != null) {
            mSelectedRecipeStepIndex = savedInstanceState.getInt(SELECTED_STEP_INDEX);
        }
        Log.d(TAG, "onCreate(), index is now: " + mSelectedRecipeStepIndex);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.navigation_buttons_container, NavigationFragment.newInstance())
                .commit();


        // Create new fragments only if no previously saved state
//        if (savedInstanceState == null) {
//            // TODO: Different layout for the tablet
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//
//            if (isMediaAvailableToShow()) {
//                MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
//                mediaPlayerFragment.setVideoURL(mStepsItem.getVideoURL());
//                mediaPlayerFragment.setmThumbnailURL(mStepsItem.getThumbnailURL());
//
//                fragmentManager.beginTransaction()
//                        .add(R.id.media_player_container, mediaPlayerFragment)
//                        .commit();
//            } else {
//                hideMediaPlayer();
//            }
//
//
//            StepInstructionsFragment instructionsFragment = new StepInstructionsFragment();
//            if (mStepsItem != null) {
//                instructionsFragment.setInstructions(mStepsItem.getDescription());
//            } else if (mIngredients != null) {
//                instructionsFragment.setIngredients(mIngredients);
//            }
//
//            fragmentManager.beginTransaction()
//                    .add(R.id.step_instructions_container, instructionsFragment)
//                    .commit();
//
//            //TODO: nav Fragment
//        }
//
//        if (mStepsItem != null) {
//            getSupportActionBar().setTitle(mStepsItem.getShortDescription());
//        } else {
//            getSupportActionBar().setTitle(getString(R.string.ingredients));
//        }
//
//        if (!isMediaAvailableToShow()) {
//            hideMediaPlayer();
//        }
//    }

//    private boolean isMediaAvailableToShow() {
//        return mStepsItem != null && (mStepsItem.getVideoURL().length() > 0 || mStepsItem.getThumbnailURL().length() > 0);
//    }
//
//    private void hideMediaPlayer() {
//        View v = findViewById(R.id.media_player_container);
//        v.setVisibility(View.GONE);
//    }
    }
//
//    // FIXME: Little hack to keep the data still in place when going back to previous activity
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        finish();
//        return true;
//    }

    @Override
    public void previousButtonPressed() {
        mSelectedRecipeStepIndex--;
        Log.d(TAG, "previousButtonPressed, index is now: " + mSelectedRecipeStepIndex);
    }

    @Override
    public void nextButtonPressed() {
        mSelectedRecipeStepIndex++;
        Log.d(TAG, "nextButtonPressed, index is now: " + mSelectedRecipeStepIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_STEP_INDEX, mSelectedRecipeStepIndex);
    }
}
