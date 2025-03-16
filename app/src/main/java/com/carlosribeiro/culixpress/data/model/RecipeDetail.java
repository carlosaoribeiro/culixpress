package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;

public class RecipeDetail {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("instructions")
    private String instructions;

    // Construtor
    public RecipeDetail(int id, String title, String image, String instructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.instructions = instructions;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
