package com.amannawabi.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.amannawabi.bakingtime.Model.Ingredient;
import com.amannawabi.bakingtime.Utils.IngredientFragment;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {
    public static String INGREDIENTS_EXTRA = "ingredient_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent =  getIntent();
        if (intent == null) {
            closeOnError();
        }

        List<Ingredient> mIngredients = intent.getParcelableArrayListExtra(INGREDIENTS_EXTRA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IngredientFragment.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) mIngredients);
        ingredientFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment,ingredientFragment)
                .commit();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

}
