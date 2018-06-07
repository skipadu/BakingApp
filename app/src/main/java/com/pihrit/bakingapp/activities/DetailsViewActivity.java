package com.pihrit.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.pihrit.bakingapp.R;
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

    private static final String SELECTED_STEP_INDEX = "current-step-index";

    private Recipe mRecipe;
    private StepsItem mStepsItem;
    private ArrayList<IngredientsItem> mIngredients;
    private int mSelectedRecipeStepIndex;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        Intent callerIntent = getIntent();

        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            mRecipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);
            mSelectedRecipeStepIndex = callerIntent.getExtras().getInt(DetailsActivity.EXTRA_STEP_INDEX);
            setInformation();
        }

        if (savedInstanceState != null) {
            mSelectedRecipeStepIndex = savedInstanceState.getInt(SELECTED_STEP_INDEX);
        }

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            String videoUrl = getVideoUrl();
            if (videoUrl != null && videoUrl.length() > 0) {
                fm.beginTransaction()
                        .add(R.id.media_player_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                        .commit();
            }

            if (mStepsItem != null) {
                fm.beginTransaction()
                        .add(R.id.step_instructions_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                        .commit();
            } else if (mIngredients != null && mIngredients.size() > 0) {
                fm.beginTransaction()
                        .add(R.id.step_ingredients_container, IngredientsFragment.newInstance(mIngredients), IngredientsFragment.TAG)
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
        return mRecipe != null ? mRecipe.getSteps().size() - 1 : 0;
    }

    @Override
    public void previousButtonPressed() {
        boolean goingBackToRecipeSelection = false;
        mSelectedRecipeStepIndex--;

        if (mSelectedRecipeStepIndex > DetailsActivity.INGREDIENTS_INDEX && mSelectedRecipeStepIndex < 0) {
            mSelectedRecipeStepIndex = DetailsActivity.INGREDIENTS_INDEX;
        } else if (mSelectedRecipeStepIndex < DetailsActivity.INGREDIENTS_INDEX) {
            goingBackToRecipeSelection = true;
        }

        if (goingBackToRecipeSelection) {
            goBackToRecipeSelection();
        } else {
            if (mSelectedRecipeStepIndex >= 0) {
                loadStep();
            } else {
                loadIngredientsStep();
            }
        }
    }

    private void loadIngredientsStep() {
        if (mIngredients != null && mIngredients.size() > 0) {
            FragmentManager fm = getSupportFragmentManager();

            Fragment ingredientsFrag = fm.findFragmentByTag(IngredientsFragment.TAG);
            if (ingredientsFrag != null) {
                fm.beginTransaction()
                        .replace(R.id.step_ingredients_container, IngredientsFragment.newInstance(mIngredients), IngredientsFragment.TAG)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.step_ingredients_container, IngredientsFragment.newInstance(mIngredients), IngredientsFragment.TAG)
                        .commit();
            }


            Fragment mediaPlayerFrag = fm.findFragmentByTag(MediaPlayerFragment.TAG);
            if (mediaPlayerFrag != null) {
                fm.beginTransaction()
                        .remove(mediaPlayerFrag)
                        .commit();
            }

            Fragment instructionsFrag = fm.findFragmentByTag(InstructionsFragment.TAG);
            if (instructionsFrag != null) {
                fm.beginTransaction()
                        .remove(instructionsFrag)
                        .commit();
            }
        }
    }

    @Override
    public void nextButtonPressed() {
        boolean goingBackToRecipeSelection = false;
        mSelectedRecipeStepIndex++;
        if (mSelectedRecipeStepIndex > getMaximumStepIndex()) {
            goingBackToRecipeSelection = true;
        } else if (mSelectedRecipeStepIndex > DetailsActivity.INGREDIENTS_INDEX && mSelectedRecipeStepIndex < 0) {
            mSelectedRecipeStepIndex = 0;
        }

        if (goingBackToRecipeSelection) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, R.string.recipe_finished, Toast.LENGTH_LONG);
            mToast.show();

            goBackToRecipeSelection();
        } else {
            loadStep();
        }
    }

    private void loadStep() {
        if (mSelectedRecipeStepIndex >= 0 && mSelectedRecipeStepIndex <= getMaximumStepIndex()) {
            mStepsItem = mRecipe.getSteps().get(mSelectedRecipeStepIndex);
            String videoUrl = getVideoUrl();

            FragmentManager fm = getSupportFragmentManager();

            Fragment ingredientsFrag = fm.findFragmentByTag(IngredientsFragment.TAG);
            if (ingredientsFrag != null) {
                fm.beginTransaction()
                        .remove(ingredientsFrag)
                        .commit();
            }

            Fragment mediaPlayerFrag = fm.findFragmentByTag(MediaPlayerFragment.TAG);
            if (videoUrl != null && videoUrl.length() > 0) {
                if (mediaPlayerFrag != null) {
                    fm.beginTransaction()
                            .replace(R.id.media_player_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                            .commit();
                } else {
                    fm.beginTransaction()
                            .add(R.id.media_player_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                            .commit();
                }
            } else if (mediaPlayerFrag != null) {
                fm.beginTransaction()
                        .remove(mediaPlayerFrag)
                        .commit();
            }
            Fragment instructionsFrag = fm.findFragmentByTag(InstructionsFragment.TAG);
            if (instructionsFrag != null) {
                fm.beginTransaction()
                        .replace(R.id.step_instructions_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.step_instructions_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                        .commit();
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent detailsIntent = new Intent(DetailsViewActivity.this, DetailsActivity.class);
            detailsIntent.putExtra(Recipe.PARCELABLE_ID, mRecipe);
            detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(detailsIntent);
            finish();
        }
        return true;
    }
}
