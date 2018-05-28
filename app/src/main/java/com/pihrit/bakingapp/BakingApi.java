package com.pihrit.bakingapp;

import com.pihrit.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BakingApi {

    @GET
    Call<ArrayList<Recipe>> getRecipes(@Url String url);
}
