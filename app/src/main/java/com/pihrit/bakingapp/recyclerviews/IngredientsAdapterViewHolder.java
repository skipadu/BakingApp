package com.pihrit.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pihrit.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_ingredient_quantity)
    public TextView mQuantityTextView;
    @BindView(R.id.tv_ingredient_measure)
    public TextView mMeasureTextView;
    @BindView(R.id.tv_ingredient)
    public TextView mIngredientTextView;

    public IngredientsAdapterViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
