package com.pihrit.bakingapp.recyclerviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapterViewHolder> {
    private final Context mContext;

    public RecipeAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        v.setFocusable(true);
        return new RecipeAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        // TODO: get item from the list
        holder.mRecipeNameTextView.setText("Recipe name");
    }

    @Override
    public int getItemCount() {
        // TODO: get count from the list
        return 30;
    }
}
