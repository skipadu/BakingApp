package com.pihrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.recyclerviews.RecipeAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeItemClickListener {

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private Toast mToast;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Improving performance, as content is not going to change size of the layout
        mRecipesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecipesRecyclerView.setLayoutManager(lm);

        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);

        // TODO: parcelable to save recipes to state

        loadRecipesFromAPI();
    }

    private void loadRecipesFromAPI() {
        // url to .json
        // http://go.udacity.com/android-baking-app-json
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://go.udacity.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BakingApi request = rf.create(BakingApi.class);

        Call<ArrayList<Recipe>> call = request.getRecipes("/android-baking-app-json");
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    // OK
                    ArrayList<Recipe> recipes = response.body();

                    mRecipeAdapter.setRecipes(recipes);
                    mRecipeAdapter.notifyDataSetChanged();
                    Log.v(TAG, "Response was successfull!");
                } else {
                    mToast = Toast.makeText(getApplicationContext(), "Response not successful!", Toast.LENGTH_LONG);
                    mToast.show();
                    Log.e(TAG, "Response was not successful. Response errorBody: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    @Override
    public void onRecipeItemClick(int itemIndex) {
        Recipe clickedRecipe = mRecipeAdapter.getRecipeAt(itemIndex);

        Intent recipeStepSelectIntent = new Intent(MainActivity.this, RecipeStepSelectActivity.class);
        recipeStepSelectIntent.putExtra(Recipe.PARCELABLE_ID, clickedRecipe);
        startActivity(recipeStepSelectIntent);
    }
}
