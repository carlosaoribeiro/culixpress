package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {

    @SerializedName("results")
    private List<Recipe> recipes;

    public RecipeResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<com.carlosribeiro.culixpress.model.Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
