package com.amannawabi.bakingtime;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.widget.Toast;

import com.amannawabi.bakingtime.IdleRes.SimpleIdelingResource;
import com.amannawabi.bakingtime.Model.Recipe;
import com.amannawabi.bakingtime.Utils.RecipeClient;
import com.amannawabi.bakingtime.Utils.RecipesAdapter;
import com.amannawabi.bakingtime.Utils.RetrofitClient;
import com.amannawabi.bakingtime.Widget.UpdateService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ItemListener {

    static private String Tag = MainActivity.class.getSimpleName();
    public static String RECIPES_EXTRA = "recipes_extra";
    private RecyclerView.LayoutManager mLayoutManager;
    List<Recipe> mRecipes = new ArrayList<>();
    @BindView(R.id.recipe_rv)
    RecyclerView mRecipeRv;

    @Nullable
    private SimpleIdelingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdelingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getIdlingResource();

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_EXTRA);
            setUpRecyclerView();
        } else {
            mIdlingResource.setIdleState(false);
            setUpRecipeData();
        }
    }


    public void setUpRecyclerView() {

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }

        boolean istablet = IsTablet(this);
        if (istablet) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mLayoutManager = new GridLayoutManager(this, 2);
            } else {
                mLayoutManager = new LinearLayoutManager(this);
            }
        }
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecipesAdapter recipesAdapter = new RecipesAdapter(this, this, mRecipes);
//        RecyclerView recyclerView = findViewById(R.id.recipe_rv);

        mRecipeRv.setLayoutManager(mLayoutManager);
        mRecipeRv.setAdapter(recipesAdapter);
    }

    public void setUpRecipeData() {
        RecipeClient client = new RetrofitClient().getClient().create(RecipeClient.class);
        Call<List<Recipe>> call = client.get_recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes = response.body();
                mIdlingResource.setIdleState(true);
                setUpRecyclerView();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                //Show alert dialog
                Log.e("Error", "Error in retrofit");
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.connection_error_title));
                dialog.setMessage(getString(R.string.connection_error));
                dialog.setPositiveButton(getString(R.string.reload_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setUpRecipeData();
                    }
                });
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(), "Item is clicked at " + position, Toast.LENGTH_SHORT).show();
        Recipe mRecipeItemClicked = mRecipes.get(position);
        Intent detailRecipeListIntent = new Intent(MainActivity.this, DetailRecipeListActivity.class);
        detailRecipeListIntent.putExtra(DetailRecipeListActivity.RECIPE_EXTRA, mRecipeItemClicked);

        UpdateService.startActionUpdateIngredients(getApplicationContext(), mRecipeItemClicked.getName().toString(), mRecipeItemClicked.getIngredients());
        startActivity(detailRecipeListIntent);
    }

    public static boolean IsTablet(Context context)
    {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        double wInches = displayMetrics.widthPixels / (double)displayMetrics.densityDpi;
        double hInches = displayMetrics.heightPixels / (double)displayMetrics.densityDpi;

        double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));
        return (screenDiagonal >= 7.0);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_EXTRA, (ArrayList<Recipe>) mRecipes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}