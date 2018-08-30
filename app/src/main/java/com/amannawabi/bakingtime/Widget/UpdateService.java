package com.amannawabi.bakingtime.Widget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.amannawabi.bakingtime.Model.Ingredient;

import java.util.List;

public class UpdateService extends IntentService {


    public static final String ACTION_UPDATE_WIDGET = "com.example.ed139.bakingapp.widget.action.update_widget";
    private static String sName;
    private static List<Ingredient> sIngredientsList;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p>
     * Used to name the worker thread, important only for debugging.
     * threw an illegal exception when I kept the auto-generated constructor
     */
    public UpdateService() {
        super("UpdateWidget");
    }

    public static void startActionUpdateIngredients(Context context, String name, List<Ingredient> ingredientsList) {

        // update if there's new info
        if (name != null) {
            sName = name;
            sIngredientsList = ingredientsList;
        }

        // start the intent
        // was stopping here before I added service to the manifest
        Intent intent = new Intent(context, UpdateService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                updateIngredientsWidget();
            }
        }
    }

    // method that updates the ingredients @Fer
    private void updateIngredientsWidget() {
        // get the widget manager and update with info from intent
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidget.class));
        BakingTimeWidget.updateAllIngredientWidgets(this, appWidgetManager, appWidgetsIds, sName, sIngredientsList);
    }
}
