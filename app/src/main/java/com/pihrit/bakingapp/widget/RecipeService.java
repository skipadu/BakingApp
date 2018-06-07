package com.pihrit.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.pihrit.bakingapp.R;

public class RecipeService extends IntentService {
    public static final String ACTION_UPDATE_RECIPE = "recipe-update";

    public RecipeService() {
        super(RecipeService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RecipeService.ACTION_UPDATE_RECIPE.equals(action)) {
                handleActionUpdateRecipe();
            }
        }
    }

    public static void startActionUpdateRecipe(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(RecipeService.ACTION_UPDATE_RECIPE);
        context.startService(intent);
    }

    private void handleActionUpdateRecipe() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_grid_view);
        IngredientsWidgetProvider.updateAllWidgets(this, appWidgetManager, appWidgetIds);
    }
}
