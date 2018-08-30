package com.amannawabi.bakingtime.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.amannawabi.bakingtime.Model.Ingredient;
import com.amannawabi.bakingtime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeWidget extends AppWidgetProvider {

    public static final String LOG_TAG = "UpdateWidget";
    private static String mName;
    public static List<Ingredient> mIngredientsList;
//    public static AppDatabase mDb;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String name, List<Ingredient> ingredientsList) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_baking_time);

        // how to get the last recipe saved?
//        mDb = AppDatabase.getInstance(context);
//        mDb.recipesDao().loadRecipes();

        mName = name;
        mIngredientsList = ingredientsList;

        if (name == null) {
            views.setTextViewText(R.id.ingredients_header, "BakingTime");
        } else {

            // ingredients head is the name of the current recipe
            views.setTextViewText(R.id.ingredients_header, name);

            // info I want to pass to the RemoteViewsFactory
            Intent intentRVService = new Intent(context, IngredientServiceWidget.class);
            intentRVService.putExtra("ingredients", ingredientsList.size());

            // create array lists for the ingredient details
            ArrayList<String> ingredient = new ArrayList<>();
            ArrayList<String> quantity = new ArrayList<>();
            ArrayList<String> measure = new ArrayList<>();


            // loop through the quantity, measure, and ingredient lists
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredient.add(ingredientsList.get(i).getIngredient());
                quantity.add(String.valueOf(ingredientsList.get(i).getQuantity()));
                measure.add(ingredientsList.get(i).getMeasure());

            }

            // put the string lists on the service intent
            intentRVService.putStringArrayListExtra("ingredient", ingredient);
            intentRVService.putStringArrayListExtra("quantity", quantity);
            intentRVService.putStringArrayListExtra("measure", measure);


            // does it matter that I used widget_list here instead of the simple_list?
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredients_list);
            views.setRemoteAdapter(R.id.widget_ingredients_list, intentRVService);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // created a new update method with help from @Fer in order to pass in the name and list
    public static void updateAllIngredientWidgets(Context context, AppWidgetManager appWidgetManager,
                                                  int[] appWidgetIds, String name, List<Ingredient> ingredientsList) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, name, ingredientsList);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (mIngredientsList != null) {
            Log.d(LOG_TAG, "Should update with " + mIngredientsList.size());
        }
        UpdateService.startActionUpdateIngredients(context, mName, mIngredientsList);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

