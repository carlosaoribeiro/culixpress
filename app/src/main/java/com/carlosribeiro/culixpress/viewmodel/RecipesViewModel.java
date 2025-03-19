package com.carlosribeiro.culixpress.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.carlosribeiro.culixpress.data.repository.RecipeRepository;
import com.carlosribeiro.culixpress.model.Recipe;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private final RecipeRepository repository;
    private final MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();
    private static final String API_KEY = "95c67c60762f4590a95269477adb067f"; // 🔹 Definir chave da API aqui para evitar hardcoding em métodos

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(); // ✅ Garante que o repositório é criado corretamente
    }

    // 🔹 Método para carregar receitas fáceis
    public void loadRecipes(String apiKey) {
        repository.getEasyRecipes(API_KEY).observeForever(recipeResponse -> {
            if (recipeResponse != null && recipeResponse.getRecipes() != null) {
                Log.d("RecipesViewModel", "✅ Receitas carregadas: " + recipeResponse.getRecipes().size());
                recipesLiveData.setValue(recipeResponse.getRecipes());
            } else {
                Log.e("RecipesViewModel", "❌ Erro ao carregar receitas!");
                recipesLiveData.setValue(null);
            }
        });
    }

    // 🔹 Retorna a lista de receitas carregadas
    public LiveData<List<Recipe>> getRecipes() {
        return recipesLiveData;
    }

    // 🔹 Retorna as instruções de uma receita específica
    // Dentro do RecipesViewModel
    public LiveData<String> getRecipeInstructions(int recipeId) {
        MutableLiveData<String> instructionsLiveData = new MutableLiveData<>();

        repository.getRecipeInstructions(recipeId, "95c67c60762f4590a95269477adb067f").observeForever(response -> {
            if (response != null) {
                Log.d("RecipesViewModel", "📜 Instruções recebidas da API: " + response);
                instructionsLiveData.setValue(response);
            } else {
                Log.e("RecipesViewModel", "❌ Nenhuma instrução recebida.");
                instructionsLiveData.setValue("Instruções não disponíveis.");
            }
        });

        return instructionsLiveData;
    }

}