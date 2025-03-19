package com.carlosribeiro.culixpress.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.carlosribeiro.culixpress.data.remote.RecipesApiService;
import com.carlosribeiro.culixpress.data.remote.RetrofitClient;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.model.RecipeResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private final RecipesApiService apiService;
    private final MutableLiveData<RecipeResponse> recipeData = new MutableLiveData<>();

    public RecipeRepository() {
        this.apiService = RetrofitClient.getClient().create(RecipesApiService.class);
    }

    public LiveData<RecipeResponse> getEasyRecipes(String apiKey) {
        fetchRecipes(apiKey);
        return recipeData;
    }



    private void fetchRecipes(String apiKey) {
        apiService.getEasyRecipes("easy", 10, apiKey).enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recipeData.setValue(response.body());
                } else {
                    Log.e("RecipeRepository", "❌ Erro ao carregar receitas: " + response.message());
                    recipeData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e("RecipeRepository", "❌ Falha na requisição: " + t.getMessage());
                recipeData.setValue(null);
            }
        });
    }

    // 🔹 Busca instruções da receita pelo ID
    public LiveData<String> getRecipeInstructions(int recipeId, String apiKey) {
        MutableLiveData<String> instructionsLiveData = new MutableLiveData<>();

        apiService.getRecipeDetails(recipeId, apiKey).enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Recipe recipe = response.body();
                    Log.d("RecipeRepository", "✅ Receita recebida: " + recipe.getTitle());

                    // 🔹 Verifica se `analyzedInstructions` existe e tem dados
                    List<Recipe.AnalyzedInstruction> analyzedInstructions = recipe.getAnalyzedInstructions();
                    if (analyzedInstructions == null) {
                        analyzedInstructions = new ArrayList<>();
                    }


                    if (!analyzedInstructions.isEmpty()) {
                        StringBuilder steps = new StringBuilder();
                        for (Recipe.AnalyzedInstruction instruction : analyzedInstructions) {
                            if (instruction.getSteps() != null) {
                                for (Recipe.Step step : instruction.getSteps()) {
                                    steps.append("• ").append(step.getStep()).append("\n");
                                }
                            }
                        }
                        instructionsLiveData.setValue(steps.toString());
                        Log.d("RecipeRepository", "📜 Instruções detalhadas extraídas.");
                    }
                    // 🔹 Se `analyzedInstructions` estiver vazio, usa `instructions`
                    else if (recipe.getInstructions() != null && !recipe.getInstructions().trim().isEmpty()) {
                        instructionsLiveData.setValue(recipe.getInstructions());
                        Log.d("RecipeRepository", "📜 Instruções simples extraídas.");
                    }
                    // 🔹 Se nada estiver disponível, tenta um fallback
                    else {
                        Log.e("RecipeRepository", "❌ Nenhuma instrução encontrada. Tentando fallback...");
                        fetchFallbackInstructions(recipeId, apiKey, instructionsLiveData);
                    }
                } else {
                    Log.e("RecipeRepository", "❌ Falha ao buscar dados da receita.");
                    instructionsLiveData.setValue("Instruções não disponíveis.");
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e("RecipeRepository", "❌ Falha ao buscar instruções: " + t.getMessage());
                instructionsLiveData.setValue("Erro ao carregar instruções.");
            }
        });

        return instructionsLiveData;
    }


    private void fetchFallbackInstructions(int recipeId, String apiKey, MutableLiveData<String> instructionsLiveData) {
        apiService.getRecipeInstructions(recipeId, apiKey).enqueue(new Callback<List<Recipe.AnalyzedInstruction>>() {
            @Override
            public void onResponse(Call<List<Recipe.AnalyzedInstruction>> call, Response<List<Recipe.AnalyzedInstruction>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    StringBuilder steps = new StringBuilder();
                    for (Recipe.AnalyzedInstruction instr : response.body()) {
                        for (Recipe.Step step : instr.getSteps()) {
                            steps.append("• ").append(step.getStep()).append("\n");
                        }
                    }
                    Log.d("RecipeRepository", "📜 Instruções extraídas: " + steps.toString());
                    instructionsLiveData.setValue(steps.toString());
                } else {
                    Log.e("RecipeRepository", "❌ API retornou lista vazia.");
                    instructionsLiveData.setValue("Instruções não disponíveis.");
                }
            }

            @Override
            public void onFailure(Call<List<Recipe.AnalyzedInstruction>> call, Throwable t) {
                Log.e("RecipeRepository", "❌ Erro ao buscar instruções: " + t.getMessage());
                instructionsLiveData.setValue("Erro ao carregar instruções.");
            }
        });
    }
}
