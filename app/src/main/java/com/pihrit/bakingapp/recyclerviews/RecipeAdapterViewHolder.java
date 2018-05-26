package com.pihrit.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pihrit.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_recipeName)
    TextView mRecipeNameTextView;

    public RecipeAdapterViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
