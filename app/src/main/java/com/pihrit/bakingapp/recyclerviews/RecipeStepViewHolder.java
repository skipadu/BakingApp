package com.pihrit.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pihrit.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_recipe_step_description)
    TextView mRecipeStepDescriptionTextView;
    final RecipeStepItemClickListener mRecipeStepClickListener;

    public RecipeStepViewHolder(View v, RecipeStepItemClickListener clickListener) {
        super(v);
        this.mRecipeStepClickListener = clickListener;
        v.setOnClickListener(this);
        ButterKnife.bind(this, v);
    }

    @Override
    public void onClick(View v) {
        mRecipeStepClickListener.onRecipeStepItemClick(getAdapterPosition());
    }

}
