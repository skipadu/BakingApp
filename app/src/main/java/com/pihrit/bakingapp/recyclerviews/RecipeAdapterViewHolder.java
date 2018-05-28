package com.pihrit.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pihrit.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_recipeName)
    TextView mRecipeNameTextView;
    final RecipeItemClickListener mRecipeClickListener;

    public RecipeAdapterViewHolder(View v, RecipeItemClickListener clickListener) {
        super(v);
        mRecipeClickListener = clickListener;
        v.setOnClickListener(this);
        ButterKnife.bind(this, v);
    }

    @Override
    public void onClick(View v) {
        int clickPosition = getAdapterPosition();
        mRecipeClickListener.onRecipeItemClick(clickPosition);
    }
}
