package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Recipe implements Serializable { // ✅ Implementando Serializable

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("instructions")
    private String instructions;

    public String getInstructions() {
        return instructions;
    }
    public Recipe(int id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
