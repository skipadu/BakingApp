package com.pihrit.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.RecipeStepSelectActivity;
import com.pihrit.bakingapp.fragments.IngredientsFragment;
import com.pihrit.bakingapp.fragments.InstructionsFragment;
import com.pihrit.bakingapp.fragments.MediaPlayerFragment;
import com.pihrit.bakingapp.fragments.NavigationFragment;
import com.pihrit.bakingapp.fragments.OnNavigationInteractionListener;
import com.pihrit.bakingapp.model.IngredientsItem;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class DetailsViewActivity extends AppCompatActivity implements OnNavigationInteractionListener {

    public static final String ARGUMENT_VIDEO_URL = "video-url";
    public static final String ARGUMENT_INGREDIENTS = "ingredients";
    public static final String ARGUMENT_INSTRUCTIONS = "instructions";

    private static final String TAG = DetailsViewActivity.class.getSimpleName();
    private static final String SELECTED_STEP_INDEX = "current-step-index";

    private Recipe mRecipe;

    private StepsItem mStepsItem;
    private ArrayList<IngredientsItem> mIngredients;
    private int mSelectedRecipeStepIndex;
    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        Intent callerIntent = getIntent();

        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            mRecipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);
            mSelectedRecipeStepIndex = callerIntent.getExtras().getInt(DetailsActivity.EXTRA_STEP_INDEX);
            setInformation();
        }

        if (savedInstanceState != null) {
            mSelectedRecipeStepIndex = savedInstanceState.getInt(SELECTED_STEP_INDEX);
        }
        Log.d(TAG, "onCreate(), index is now: " + mSelectedRecipeStepIndex);


        // TODO: handle replacing of fragments when navigating with the buttons; now just test to show something... if it even works anymore

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            String videoUrl = getVideoUrl();
            if (videoUrl != null && videoUrl.length() > 0) {
                fm.beginTransaction()
                        .add(R.id.media_player_container, MediaPlayerFragment.newInstance(videoUrl))
                        .commit();
            }

            if (mStepsItem != null) {
                fm.beginTransaction()
                        .add(R.id.step_instructions_container, InstructionsFragment.newInstance(mStepsItem.getDescription()))
                        .commit();
            } else if (mIngredients != null && mIngredients.size() > 0) {
                fm.beginTransaction()
                        .add(R.id.step_ingredients_container, IngredientsFragment.newInstance(mIngredients))
                        .commit();
            }

            fm.beginTransaction()
                    .add(R.id.navigation_buttons_container, NavigationFragment.newInstance())
                    .commit();
        }
    }

    private void setInformation() {
        if (mRecipe != null) {

            if (mSelectedRecipeStepIndex >= 0) {
                mStepsItem = mRecipe.getSteps().get(mSelectedRecipeStepIndex);
            }
            mIngredients = mRecipe.getIngredients();
        }
    }

    private String getVideoUrl() {
        String videoUrl = null;
        if (mStepsItem != null) {
            videoUrl = mStepsItem.getVideoURL();
            if (videoUrl == null) {
                videoUrl = mStepsItem.getThumbnailURL();
            }
        }
        return videoUrl;
    }

    private int getMaximumStepIndex() {
        int maxIndex = mRecipe != null ? mRecipe.getSteps().size() - 1 : 0;
        Log.d(TAG, "Maximum step index: " + maxIndex);
        return maxIndex;
    }

    @Override
    public void previousButtonPressed() {

        // TODO: check that index is not too small to not get outOfIndex
        boolean goingBackToRecipeSelection = false;
        mSelectedRecipeStepIndex--;

        if (mSelectedRecipeStepIndex > DetailsActivity.INGREDIENTS_INDEX && mSelectedRecipeStepIndex < 0) {
            mSelectedRecipeStepIndex = DetailsActivity.INGREDIENTS_INDEX;
        } else if (mSelectedRecipeStepIndex < DetailsActivity.INGREDIENTS_INDEX) {
            goingBackToRecipeSelection = true;
        }
        Log.d(TAG, "previousButtonPressed, index is now: " + mSelectedRecipeStepIndex + " - Back to recipe selection? : " + goingBackToRecipeSelection);

        if (goingBackToRecipeSelection) {
            goBackToRecipeSelection();
        } else {
            if (mSelectedRecipeStepIndex < 0) {
                Log.d(TAG, "Ingredients step");
            } else {
                Log.d(TAG, "Normal step, index: " + mSelectedRecipeStepIndex);
            }
        }
    }

    @Override
    public void nextButtonPressed() {
        boolean goingBackToRecipeSelection = false;
        mSelectedRecipeStepIndex++;
        if (mSelectedRecipeStepIndex > getMaximumStepIndex()) {
            goingBackToRecipeSelection = true;
        }

        Log.d(TAG, "nextButtonPressed, index is now: " + mSelectedRecipeStepIndex + " - Back to recipe selection?: " + goingBackToRecipeSelection);

        if (goingBackToRecipeSelection) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Recipe finished!", Toast.LENGTH_LONG);
            mToast.show();

            goBackToRecipeSelection();
        }
    }

    private void goBackToRecipeSelection() {
        // Trying also to clear navigation stack
        Intent mainIntent = new Intent(DetailsViewActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_STEP_INDEX, mSelectedRecipeStepIndex);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent recipeStepSelectIntent = new Intent(DetailsViewActivity.this, RecipeStepSelectActivity.class);
        recipeStepSelectIntent.putExtra(Recipe.PARCELABLE_ID, mRecipe);
        startActivity(recipeStepSelectIntent);
        finish();
    }
}
