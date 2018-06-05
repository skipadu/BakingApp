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

public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_COMING_FORM_WIDGET = "coming-from-widget";
    public static final String TAG = IngredientsWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getIngredientsGridRemoteView(context);

        Log.d(TAG, "updateAppWidget()");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getIngredientsGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        Intent serviceIntent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredients_grid_view, serviceIntent);

        Intent clickIntent = new Intent(context, MainActivity.class);
        clickIntent.putExtra(EXTRA_COMING_FORM_WIDGET, true);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_placeholder, clickPendingIntent);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
        String recipeName = (String) sharedPreferencesUtil.getObject(MainActivity.PREF_RECIPE_NAME, String.class);
        if (recipeName != null) {
            views.setTextViewText(R.id.widget_tv_recipe_name, recipeName);
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

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

