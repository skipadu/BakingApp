package com.pihrit.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pihrit.bakingapp.BakingApi;
import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.SharedPreferencesUtil;
import com.pihrit.bakingapp.model.Recipe;
import com.pihrit.bakingapp.recyclerviews.RecipeAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeItemClickListener;
import com.pihrit.bakingapp.widget.IngredientsWidgetProvider;
import com.pihrit.bakingapp.widget.RecipeService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeItemClickListener {
    public static final String PREF_INGREDIENTS = "ingredients-pref";
    public static final String PREF_RECIPE_NAME = "recipe-name-pref";

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "http://go.udacity.com";
    private static final String BAKING_JSON_URL = "/android-baking-app-json";
    private static final int GRID_SPAN_COUNT = 2;

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private Toast mToast;

    @Nullable
    private CountingIdlingResource mCountingIdlingResource;
    private boolean isComingFromTheWidget;

    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getCountingIdlingResource() {
        if (mCountingIdlingResource == null) {
            mCountingIdlingResource = new CountingIdlingResource(TAG);
        }
        return mCountingIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent callerIntent = getIntent();
        if (callerIntent.hasExtra(IngredientsWidgetProvider.EXTRA_COMING_FORM_WIDGET)) {
            Log.d(TAG, "Coming from the widget!");
            isComingFromTheWidget = true;
            // TODO: show some UI-text, instruction, to user that she/he should select recipe
        }

        // Improving performance, as content is not going to change size of the layout
        mRecipesRecyclerView.setHasFixedSize(true);

        if (isTabletLayout()) {
            RecyclerView.LayoutManager lm = new GridLayoutManager(this, GRID_SPAN_COUNT);
            mRecipesRecyclerView.setLayoutManager(lm);
        } else {

            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            mRecipesRecyclerView.setLayoutManager(lm);
        }

        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);

        // TODO: parcelable to save recipes to state

        loadRecipesFromAPI();
    }

    private boolean isTabletLayout() {
        return findViewById(R.id.root_main_tablet) != null;
    }

    private void loadRecipesFromAPI() {
        getCountingIdlingResource().increment();

        Retrofit rf = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BakingApi request = rf.create(BakingApi.class);

        Call<ArrayList<Recipe>> call = request.getRecipes(BAKING_JSON_URL);
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Recipe> recipes = response.body();

                    mRecipeAdapter.setRecipes(recipes);
                    mRecipeAdapter.notifyDataSetChanged();
                    Log.v(TAG, "Response was successfull!");
                } else {
                    mToast = Toast.makeText(getApplicationContext(), "Response not successful!", Toast.LENGTH_LONG);
                    mToast.show();
                    Log.e(TAG, "Response was not successful. Response errorBody: " + response.errorBody().toString());
                }
                getCountingIdlingResource().decrement();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                getCountingIdlingResource().decrement();
            }
        });

    }

    @Override
    public void onRecipeItemClick(int itemIndex) {
        Recipe clickedRecipe = mRecipeAdapter.getRecipeAt(itemIndex);

        if (isComingFromTheWidget) {
            // TODO: store ingredients and name of this recipe to somewhere so it can be shown in widget
            SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);
            sharedPreferencesUtil.storeObjects(PREF_INGREDIENTS, clickedRecipe.getIngredients());
            sharedPreferencesUtil.storeObject(PREF_RECIPE_NAME, clickedRecipe.getName());

//            Intent intent = new Intent(this, IngredientsWidgetProvider.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//
//            int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//            sendBroadcast(intent);

            RecipeService.startActionUpdateRecipe(this);

            mToast = Toast.makeText(this, "Recipe stored for widget!", Toast.LENGTH_LONG);
            mToast.show();
//            finish();
        } else {
            Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
            detailsIntent.putExtra(Recipe.PARCELABLE_ID, clickedRecipe);
            startActivity(detailsIntent);
        }
    }

}
