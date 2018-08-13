package com.amannawabi.bakingtime.Utils;


import com.amannawabi.bakingtime.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeClient {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> get_recipes();
}