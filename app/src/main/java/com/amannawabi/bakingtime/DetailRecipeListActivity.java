package com.amannawabi.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amannawabi.bakingtime.Model.Recipe;
import com.amannawabi.bakingtime.Model.Step;
import com.amannawabi.bakingtime.Utils.DetailRecipeListFragment;
import com.amannawabi.bakingtime.Utils.IngredientFragment;
import com.amannawabi.bakingtime.Utils.ListAdapter;
import com.amannawabi.bakingtime.Utils.PlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class DetailRecipeListActivity extends AppCompatActivity implements ListAdapter.ItemListener {
    public static String RECIPE_EXTRA = "recipes_extra";
    private boolean mTwoPane;
    android.support.v4.app.FragmentManager mFragmentManager;
    Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe_list);
        mFragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            recipe = intent.getParcelableExtra(RECIPE_EXTRA);

            Bundle bundle = new Bundle();
            bundle.putStringArrayList(DetailRecipeListFragment.LIST_NAMES_EXTRA, recipe.getShortDescriptionsFromSteps());
            DetailRecipeListFragment listFragment = new DetailRecipeListFragment();
            listFragment.setArguments(bundle);

            mFragmentManager.beginTransaction()
                    .add(R.id.detail_recipe_list_fragment, listFragment)
                    .commit();
        } else {
            recipe = savedInstanceState.getParcelable(RECIPE_EXTRA);
        }

        setTitle(recipe.getName());
        mTwoPane = findViewById(R.id.step_player_fragment) != null;
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(int position) {
        if (!mTwoPane) {
            if (position == 0) {
                Intent ingredientsIntent = new Intent(DetailRecipeListActivity.this, IngredientActivity.class);
                ingredientsIntent.putParcelableArrayListExtra(IngredientActivity.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) recipe.getIngredients());
                startActivity(ingredientsIntent);
            } else {
                Intent stepIntent = new Intent(DetailRecipeListActivity.this, StepActivity.class);
                stepIntent.putParcelableArrayListExtra(StepActivity.STEPS_EXTRA, (ArrayList<? extends Parcelable>) recipe.getSteps());
                stepIntent.putExtra(StepActivity.POS_EXTRA, position - 1);
                stepIntent.putExtra(StepActivity.RECIPE_NAME_EXTRA, recipe.getName());
                startActivity(stepIntent);
            }
        }
        else {
            if (position == 0) {
                IngredientFragment ingredientsFragment = new IngredientFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(IngredientFragment.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) recipe.getIngredients());
                ingredientsFragment.setArguments(bundle);

                mFragmentManager.beginTransaction()
                        .replace(R.id.step_player_fragment,ingredientsFragment)
                        .commit();
            }else{
                List<Step> steps = recipe.getSteps();
                int stepPos = position - 1;
                String url0 = steps.get(stepPos).getVideoURL();
                String url1 = steps.get(stepPos).getThumbnailURL();
                String videoUrl = (url1.equals(""))?url0:url1;
                String stepDescription = steps.get(stepPos).getDescription();

                Bundle bundle = new Bundle();
                bundle.putString(PlayerFragment.DESCRIPTION_EXTRA,stepDescription);
                bundle.putString(PlayerFragment.VIDEO_URL_EXTRA,videoUrl);
                PlayerFragment playerFragment = new PlayerFragment();
                playerFragment.setArguments(bundle);

                mFragmentManager.beginTransaction()
                        .replace(R.id.step_player_fragment, playerFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_EXTRA, recipe);
    }
}