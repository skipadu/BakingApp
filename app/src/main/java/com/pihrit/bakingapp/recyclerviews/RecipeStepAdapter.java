package com.pihrit.bakingapp.recyclerviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.model.StepsItem;

import java.util.ArrayList;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {
    private final Context mContext;
    private final RecipeStepItemClickListener mRecipeStepClickListener;
    private ArrayList<StepsItem> mRecipeSteps;

    public RecipeStepAdapter(Context context, RecipeStepItemClickListener clickListener) {
        this.mContext = context;
        this.mRecipeStepClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_step, parent, false);
        v.setFocusable(true);
        return new RecipeStepViewHolder(v, mRecipeStepClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        StepsItem stepsItem = getStepsItemAt(position);
        holder.mRecipeStepDescriptionTextView.setText(stepsItem.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps == null ? 0 : mRecipeSteps.size();
    }

    public void setRecipeSteps(ArrayList<StepsItem> recipeSteps) {
        this.mRecipeSteps = recipeSteps;
    }

    public StepsItem getStepsItemAt(int index) {
        return mRecipeSteps.get(index);
    }
}
