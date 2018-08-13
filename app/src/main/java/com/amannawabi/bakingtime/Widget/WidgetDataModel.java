package com.amannawabi.bakingtime.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amannawabi.bakingtime.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WidgetDataModel {
    public static String RECIPE_KEY = "rec";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefsEditor.putString(RECIPE_KEY, json);
        prefsEditor.commit();
    }

    public static Recipe getRecipe(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(RECIPE_KEY, null);
        Type type = new TypeToken<Recipe>() {}.getType();
        return gson.fromJson(json, type);
    }
}