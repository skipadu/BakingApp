package com.pihrit.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.fragments.OnStepInteractionListener;
import com.pihrit.bakingapp.fragments.StepsFragment;
import com.pihrit.bakingapp.model.Recipe;

import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements OnStepInteractionListener {
    public static final String ARGUMENT_RECIPE = "recipe";
    public static final int INGREDIENTS_INDEX = -10;
    public static final String EXTRA_STEP_INDEX = "step-index";
    private static final String BUNDLE_RECIPE = "recipe";
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private Recipe mRecipe;

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
                // TODO: Need to handle the current selected step index
                // If ingredients
//            ArrayList<IngredientsItem> ingredients;
//            fm.beginTransaction()
//                    .add(R.id.ingredients_fragment_container, IngredientsFragment.newInstance(ingredients))
//                    .commit();
                //

                // else
//            String videoUrl;
//            String instructions;
//            fm.beginTransaction()
//                    .add(R.id.media_player_fragment_container, MediaPlayerFragment.newInstance(videoUrl))
//                    .add(R.id.instructions_fragment_container, InstructionsFragment.newInstance(instructions))
//                    .commit();

            }
        }

    }


    private boolean isTabletLayout() {
        return findViewById(R.id.root_details_tablet) != null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECIPE, mRecipe);
    }

    @Override
    public void recipeClicked(int index) {
        Log.d(TAG, "Recipe clicked, index: " + index);
        if (isTabletLayout()) {

        } else {
            startDetailsViewActivity(index);
        }
    }

    @Override
    public void ingredientsClicked() {
        Log.d(TAG, "Ingredients clicked");
        if (isTabletLayout()) {

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
