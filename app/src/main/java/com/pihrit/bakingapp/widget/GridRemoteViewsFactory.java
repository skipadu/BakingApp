package com.pihrit.bakingapp.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.SharedPreferencesUtil;
import com.pihrit.bakingapp.activities.MainActivity;
import com.pihrit.bakingapp.model.IngredientsItem;

import java.util.ArrayList;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = GridRemoteViewsFactory.class.getSimpleName();

    private final Context mContext;
    private ArrayList<IngredientsItem> mIngredients;

    public GridRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged()");

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(mContext);
        mIngredients = sharedPreferencesUtil.loadObjects(MainActivity.PREF_INGREDIENTS, IngredientsItem.class);
    }

    @Override
    public void onDestroy() {
        mIngredients = null;
    }

    @Override
    public int getCount() {
        return mIngredients != null ? mIngredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mIngredients == null || mIngredients.size() == 0) {
            return null;
        }
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);
        IngredientsItem currentIngredient = mIngredients.get(i);
        if (currentIngredient != null) {
            views.setTextViewText(R.id.widget_item_tv_quantity, String.valueOf(currentIngredient.getQuantity()));
            views.setTextViewText(R.id.widget_item_tv_measure, currentIngredient.getMeasure());
            views.setTextViewText(R.id.widget_item_tv_ingredient, currentIngredient.getIngredient());

            // Hide placeholder
            views.setViewVisibility(R.id.widget_placeholder, View.GONE);
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
