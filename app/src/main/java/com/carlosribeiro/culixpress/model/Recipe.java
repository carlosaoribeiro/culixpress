package com.carlosribeiro.culixpress.model;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image") // Certifique-se que o nome do campo está certo
    private String imageUrl;

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
        return imageUrl; // ✅ Método correto para recuperar a imagem
    }
}
