package com.pihrit.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.pihrit.bakingapp.R;
import com.pihrit.bakingapp.SharedPreferencesUtil;
import com.pihrit.bakingapp.activities.MainActivity;
import com.pihrit.bakingapp.model.IngredientsItem;

import java.util.ArrayList;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_COMING_FORM_WIDGET = "coming-from-widget";
    public static final String TAG = IngredientsWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getIngredientsGridRemoteView(context);

        Log.d(TAG, "udpdateAppWidget(). Next we need to try load the data from sharedpreferences.");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getIngredientsGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        Intent clickIntent = new Intent(context, MainActivity.class);
        clickIntent.putExtra(EXTRA_COMING_FORM_WIDGET, true);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_placeholder, clickPendingIntent);

        // TODO: set servive as remoteAdapter


        // test to get values from sharedprefs
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
        ArrayList<IngredientsItem> ingredients = sharedPreferencesUtil.loadObjects(MainActivity.PREF_INGREDIENTS, IngredientsItem.class);
        if (ingredients != null) {

            for (IngredientsItem ingredient : ingredients) {
                Log.v(TAG, "" + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
            }
        }
        String recipeName = (String) sharedPreferencesUtil.getObject(MainActivity.PREF_RECIPE_NAME, String.class);
        if (recipeName != null) {
            Log.v(TAG, recipeName);
        }

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

