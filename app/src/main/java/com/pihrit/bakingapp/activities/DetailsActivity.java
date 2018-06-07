package com.pihrit.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.fragments.IngredientsFragment;
import com.pihrit.bakingapp.fragments.InstructionsFragment;
import com.pihrit.bakingapp.fragments.MediaPlayerFragment;
import com.pihrit.bakingapp.fragments.OnStepInteractionListener;
import com.pihrit.bakingapp.fragments.StepsFragment;
import com.pihrit.bakingapp.fragments.ThumbnailFragment;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.model.StepsItem;

import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements OnStepInteractionListener {
    public static final String ARGUMENT_RECIPE = "recipe";
    public static final int INGREDIENTS_INDEX = -10;
    public static final String EXTRA_STEP_INDEX = "step-index";
    private static final String BUNDLE_RECIPE = "recipe";
    private static final String BUNDLE_CURRENT_STEP_INDEX = "current-step-index";

    private Recipe mRecipe;
    // On start, by default we are at the ingredients step
    private int mCurrentSelectedStepIndex = INGREDIENTS_INDEX;
    private StepsItem mStepsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(Recipe.PARCELABLE_ID)) {
            mRecipe = callerIntent.getExtras().getParcelable(Recipe.PARCELABLE_ID);
        }
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(BUNDLE_RECIPE);
            mCurrentSelectedStepIndex = savedInstanceState.getInt(BUNDLE_CURRENT_STEP_INDEX);
        }

        if (mRecipe != null) {
            getSupportActionBar().setTitle(mRecipe.getName());
        }

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            fm.beginTransaction()
                    .add(R.id.steps_fragment_container, StepsFragment.newInstance(mRecipe))
                    .commit();

            if (isTabletLayout()) {
                if (mCurrentSelectedStepIndex == INGREDIENTS_INDEX) {
                    fm.beginTransaction()
                            .add(R.id.ingredients_fragment_container, IngredientsFragment.newInstance(mRecipe.getIngredients()), IngredientsFragment.TAG)
                            .commit();
                } else {
                    mStepsItem = mRecipe.getSteps().get(mCurrentSelectedStepIndex);
                    String videoUrl = getVideoUrl();
                    if (!TextUtils.isEmpty(videoUrl)) {
                        fm.beginTransaction()
                                .add(R.id.media_player_fragment_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                                .commit();
                    }
                    fm.beginTransaction()
                            .add(R.id.instructions_fragment_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                            .commit();

                    String thumbnailUrl = getThumbnailUrl();
                    if (!TextUtils.isEmpty(thumbnailUrl)) {
                        fm.beginTransaction()
                                .add(R.id.thumbnail_container, ThumbnailFragment.newInstance(thumbnailUrl), ThumbnailFragment.TAG)
                                .commit();
                    }

                }
            }
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

    private String getThumbnailUrl() {
        String thumbnailUrl = null;
        if (mStepsItem != null) {
            thumbnailUrl = mStepsItem.getThumbnailURL();
        }
        return thumbnailUrl;
    }

    private boolean isTabletLayout() {
        return findViewById(R.id.root_details_tablet) != null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECIPE, mRecipe);
        outState.putInt(BUNDLE_CURRENT_STEP_INDEX, mCurrentSelectedStepIndex);
    }

    @Override
    public void recipeClicked(int index) {
        mCurrentSelectedStepIndex = index;
        mStepsItem = mRecipe.getSteps().get(mCurrentSelectedStepIndex);
        String videoUrl = getVideoUrl();

        if (isTabletLayout()) {

            FragmentManager fm = getSupportFragmentManager();

            Fragment ingredientsFrag = fm.findFragmentByTag(IngredientsFragment.TAG);
            if (ingredientsFrag != null) {
                fm.beginTransaction()
                        .remove(ingredientsFrag)
                        .commit();
            }

            Fragment mediaPlayerFrag = fm.findFragmentByTag(MediaPlayerFragment.TAG);
            if (mediaPlayerFrag != null) {
                if (!TextUtils.isEmpty(videoUrl)) {
                    fm.beginTransaction()
                            .replace(R.id.media_player_fragment_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                            .commit();
                } else {
                    fm.beginTransaction()
                            .remove(mediaPlayerFrag)
                            .commit();
                }
            } else {

                if (videoUrl != null && videoUrl.length() > 0) {
                    fm.beginTransaction()
                            .add(R.id.media_player_fragment_container, MediaPlayerFragment.newInstance(videoUrl), MediaPlayerFragment.TAG)
                            .commit();
                }
            }

            Fragment instructionsFrag = fm.findFragmentByTag(InstructionsFragment.TAG);
            if (instructionsFrag != null) {
                fm.beginTransaction()
                        .replace(R.id.instructions_fragment_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.instructions_fragment_container, InstructionsFragment.newInstance(mStepsItem.getDescription()), InstructionsFragment.TAG)
                        .commit();
            }

            Fragment thumbnailFragment = fm.findFragmentByTag(ThumbnailFragment.TAG);

            String thumbnailUrl = getThumbnailUrl();
            if (thumbnailFragment != null) {
                if (TextUtils.isEmpty(thumbnailUrl)) {
                    fm.beginTransaction()
                            .remove(thumbnailFragment)
                            .commit();
                } else {
                    fm.beginTransaction()
                            .replace(R.id.thumbnail_container, ThumbnailFragment.newInstance(thumbnailUrl), ThumbnailFragment.TAG)
                            .commit();
                }
            } else if (!TextUtils.isEmpty(thumbnailUrl)) {
                fm.beginTransaction()
                        .add(R.id.thumbnail_container, ThumbnailFragment.newInstance(thumbnailUrl), ThumbnailFragment.TAG)
                        .commit();
            }


        } else {
            startDetailsViewActivity(index);
        }
    }

    @Override
    public void ingredientsClicked() {
        if (isTabletLayout()) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment ingredientsFrag = fm.findFragmentByTag(IngredientsFragment.TAG);
            if (ingredientsFrag != null) {
                fm.beginTransaction()
                        .replace(R.id.ingredients_fragment_container, IngredientsFragment.newInstance(mRecipe.getIngredients()), IngredientsFragment.TAG)
                        .commit();
            } else {
                fm.beginTransaction()
                        .add(R.id.ingredients_fragment_container, IngredientsFragment.newInstance(mRecipe.getIngredients()), IngredientsFragment.TAG)
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

        } else {
            startDetailsViewActivity(INGREDIENTS_INDEX);
        }
    }

    private void startDetailsViewActivity(int index) {
        Intent detailsViewIntent = new Intent(DetailsActivity.this, DetailsViewActivity.class);
        detailsViewIntent.putExtra(Recipe.PARCELABLE_ID, mRecipe);
        detailsViewIntent.putExtra(EXTRA_STEP_INDEX, index);
        startActivity(detailsViewIntent);
    }
}
