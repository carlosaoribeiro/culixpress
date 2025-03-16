package com.carlosribeiro.culixpress.data.remote;

import com.carlosribeiro.culixpress.model.RecipeDetail;
import com.carlosribeiro.culixpress.model.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipesApiService {

    @GET("recipes/complexSearch")
    Call<RecipeResponse> getEasyRecipes(
            @Query("query") String query,
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );

    @GET("recipes/{id}/information")
    Call<RecipeDetail> getRecipeDetail(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
    );
}
