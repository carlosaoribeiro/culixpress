package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecipeResponse {

    @SerializedName("results")  // 🔥 Certifique-se de que "results" é o nome correto na API
    private List<com.carlosribeiro.culixpress.model.Recipe> recipes;

    // 🔹 Construtor padrão necessário para Gson
    public RecipeResponse() {}

    // 🔹 Construtor opcional
    public RecipeResponse(List<com.carlosribeiro.culixpress.model.Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<com.carlosribeiro.culixpress.model.Recipe> getRecipes() {  // ✅ Método correto para acessar a lista
        return recipes;
    }

    public void setRecipes(List<com.carlosribeiro.culixpress.model.Recipe> recipes) {  // ✅ Método setter, caso precise
        this.recipes = recipes;
    }
}
