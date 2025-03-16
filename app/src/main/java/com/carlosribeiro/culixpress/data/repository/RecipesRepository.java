package com.carlosribeiro.culixpress.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.carlosribeiro.culixpress.data.remote.RecipesApiService;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.model.RecipeDetail;
import com.carlosribeiro.culixpress.model.RecipeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class RecipesRepository {

    private RecipesApiService apiService;

    public RecipesRepository(RecipesApiService apiService) {
        this.apiService = apiService;
    }

    // Este é o método que estava faltando/corrigido
    public LiveData<List<Recipe>> getEasyRecipes(String apiKey) {
        MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

        apiService.getEasyRecipes("easy", 20, apiKey).enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    recipesLiveData.setValue(response.body().getRecipes());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                recipesLiveData.setValue(null);
            }
        });

        return recipesLiveData;
    }

    public LiveData<RecipeDetail> getRecipeDetail(int recipeId, String apiKey) {
        MutableLiveData<RecipeDetail> detailLiveData = new MutableLiveData<>();

        apiService.getRecipeDetail(recipeId, apiKey).enqueue(new Callback<RecipeDetail>() {
            @Override
            public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response) {
                if(response.isSuccessful() && response.body() != null) {
                    detailLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RecipeDetail> call, Throwable t) {
                detailLiveData.setValue(null);
            }
        });

        return detailLiveData;
    }
}
