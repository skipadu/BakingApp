package com.pihrit.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pihrit.bakingapp.recyclerviews.RecipeAdapter;
import com.pihrit.bakingapp.recyclerviews.RecipeItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeItemClickListener {

    // TODO recyclerview, adapter, layoutmanager

    @BindView(R.id.rv_recipes)
    RecyclerView mRecipesRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Improving performance, as content is not going to change size of the layout
        mRecipesRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        // TODO: vertical?

        mRecipesRecyclerView.setLayoutManager(lm);
        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecipesRecyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onRecipeItemClick(int itemIndex) {
        // TODO: open recipe step selection screen based on clicked item
    }
}
