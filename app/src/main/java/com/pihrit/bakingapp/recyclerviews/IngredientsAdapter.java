package com.pihrit.bakingapp.recyclerviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.model.IngredientsItem;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapterViewHolder> {
    private final Context mContext;
    private ArrayList<IngredientsItem> mIngredients;

    public IngredientsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_ingredient, parent, false);
        v.setFocusable(true);
        return new IngredientsAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder holder, int position) {
        IngredientsItem ingredient = getIngredientAt(position);
        holder.mQuantityTextView.setText(String.format("%s", ingredient.getQuantity()));
        holder.mMeasureTextView.setText(ingredient.getMeasure());
        holder.mIngredientTextView.setText(ingredient.getIngredient());
    }

    private IngredientsItem getIngredientAt(int position) {
        return mIngredients.get(position);
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    public void setIngredients(ArrayList<IngredientsItem> ingredients) {
        this.mIngredients = ingredients;
    }
}
