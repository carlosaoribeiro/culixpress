package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    // Construtor
    public Recipe(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
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

}