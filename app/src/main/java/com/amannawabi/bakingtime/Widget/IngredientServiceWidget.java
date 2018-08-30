package com.amannawabi.bakingtime.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.amannawabi.bakingtime.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientServiceWidget extends RemoteViewsService {
    private static final String TAG = "IngredientServiceWidget";
    // originally separated from WidgetDataProvider like in this webcast
    // https://www.youtube.com/watch?v=eKANzCs2pWM&feature=youtu.be
    // but had to combine, else the app caused my phones launcher to crash and bug out...
    // I think it couldn't make sense of the services declared in the Manifest

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }

    class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

        private static final String TAG = "WidgetDataProvider";

        List<Ingredient> ingredientList;
        Context mContext;

        WidgetDataProvider(Context context, Intent intent) {

            mContext = context;

            // values from intent that we have to cycle through
            int numberOfIngredients = intent.getIntExtra("ingredients", 0);

            // more array lists
            ArrayList<String> ingredient;
            ArrayList<String> quantity;
            ArrayList<String> measure;

            // add them to the intent
            ingredient = intent.getStringArrayListExtra("ingredient");
            quantity = intent.getStringArrayListExtra("quantity");
            measure = intent.getStringArrayListExtra("measure");

            // create new list
            ingredientList = new ArrayList<>();

            // add the ingredients to the list
            for (int i = 0; i < numberOfIngredients; i++) {
                Ingredient ingredients = new Ingredient(ingredient.get(i), quantity.get(i), measure.get(i));
                ingredientList.add(ingredients);
            }
        }

        @Override
        public void onCreate() {
            //? nothing
        }

        @Override
        public void onDataSetChanged() {
            ingredientList = BakingTimeWidget.mIngredientsList;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (ingredientList == null) {
                return 0;
            } else {
                return ingredientList.size();
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (ingredientList == null) {
                return null;
            } else {
                RemoteViews view = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
                Ingredient ingredient = ingredientList.get(position);
                view.setTextViewText(android.R.id.text1, ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
                return view;
            }
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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
