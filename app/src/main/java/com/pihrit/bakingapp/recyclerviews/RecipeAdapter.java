package com.pihrit.bakingapp.recyclerviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapterViewHolder> {
    private final Context mContext;
    private final RecipeItemClickListener mRecipeClickListener;
    private ArrayList<Recipe> mRecipes;

    public RecipeAdapter(Context context, RecipeItemClickListener clickListener) {
        this.mContext = context;
        this.mRecipeClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        v.setFocusable(true);
        return new RecipeAdapterViewHolder(v, mRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = getRecipeAt(position);

        if (recipe != null) {
            holder.mRecipeNameTextView.setText(recipe.getName());
            holder.mServingsTextView.setText(Integer.toString(recipe.getServings()));
            String recipeName = recipe.getImage();
            if (recipeName != null && recipeName.length() > 0) {
                Picasso.get()
                        .load(recipeName)
                        .placeholder(R.drawable.baking_placeholder)
                        .error(R.drawable.baking_placeholder_error)
                        .into(holder.mRecipeImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public Recipe getRecipeAt(int index) {
        return mRecipes.get(index);
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.mRecipes = recipes;
    }
}
