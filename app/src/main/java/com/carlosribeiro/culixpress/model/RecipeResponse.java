package com.carlosribeiro.culixpress.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {

    @SerializedName("results")
    private List<com.carlosribeiro.culixpress.model.Recipe> recipes;

    public RecipeResponse(List<com.carlosribeiro.culixpress.model.Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<com.carlosribeiro.culixpress.model.Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<com.carlosribeiro.culixpress.model.Recipe> recipes) {
        this.recipes = recipes;
    }
}
