package com.carlosribeiro.culixpress.data.remote;

import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.model.RecipeResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipesApiService {

    // 🔹 Busca receitas fáceis com base na pesquisa
    @GET("recipes/complexSearch")
    Call<RecipeResponse> getEasyRecipes(
            @Query("query") String query,  // "easy"
            @Query("number") int number,   // Quantidade de receitas
            @Query("apiKey") String apiKey // Chave da API do Spoonacular
    );

    // 🔹 Busca os detalhes completos de uma receita pelo ID
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeDetails(
            @Path("id") int recipeId,
            // 🔹 Agora corretamente anotado
            @Query("apiKey") String apiKey
    );




    // 🔹 Busca apenas as instruções detalhadas da receita pelo ID
    @GET("recipes/{id}/analyzedInstructions")
    Call<List<Recipe.AnalyzedInstruction>> getRecipeInstructions(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
    );
}
