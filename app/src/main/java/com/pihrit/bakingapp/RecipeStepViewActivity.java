package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class RecipeStepViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_view);

        // TODO: Different layout for the tablet

        FragmentManager fragmentManager = getSupportFragmentManager();

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        // TODO: add needed data for the fragment

        fragmentManager.beginTransaction()
                .add(R.id.media_player_container, mediaPlayerFragment)
                .commit();

        StepInstructionsFragment instructionsFragment = new StepInstructionsFragment();
        // TODO: add needed data for the fragment

        fragmentManager.beginTransaction()
                .add(R.id.step_instructions_container, instructionsFragment)
                .commit();

        NavigationFragment navigationFragment = new NavigationFragment();
        fragmentManager.beginTransaction()
                .add(R.id.navigation_buttons_container, navigationFragment)
                .commit();
    }


    // Little hack to keep the data still in place when going back to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
