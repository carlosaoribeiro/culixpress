package com.carlosribeiro.culixpress.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.carlosribeiro.culixpress.data.remote.ApiClient;
import com.carlosribeiro.culixpress.data.remote.RecipesApiService;
import com.carlosribeiro.culixpress.data.repository.RecipesRepository;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.model.RecipeDetail;

import java.util.List;

public class RecipesViewModel extends ViewModel {

    private final RecipesRepository repository;

    public RecipesViewModel() {
        RecipesApiService apiService = ApiClient.getRetrofitInstance().create(RecipesApiService.class);
        repository = new RecipesRepository(apiService);
    }

    // Esse é o método que faltava
    public LiveData<List<Recipe>> getEasyRecipes(String apiKey) {
        return repository.getEasyRecipes(apiKey);
    }

    // método já existente (para detalhes da receita)
    public LiveData<RecipeDetail> getRecipeDetail(int recipeId, String apiKey) {
        return repository.getRecipeDetail(recipeId, apiKey);
    }
}
