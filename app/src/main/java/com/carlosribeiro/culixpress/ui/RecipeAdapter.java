package com.carlosribeiro.culixpress.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipeList = recipes != null ? recipes : new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.title.setText(recipe.getTitle());
        holder.prepTime.setText("⏳ Tempo de preparo: " + recipe.getPreparationTime() + " min");
        holder.servings.setText("🍽️ Porções: " + recipe.getServings());

        // 🔹 Carrega a imagem com Glide
        Glide.with(holder.itemView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.recipeImage);

        // 🔹 Evento de clique para abrir os detalhes
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipeId", recipe.getId());
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("imageUrl", recipe.getImageUrl());
            intent.putExtra("prepTime", recipe.getPreparationTime());
            intent.putExtra("servings", recipe.getServings());
            intent.putExtra("instructions", recipe.getFormattedInstructions());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> newRecipes) {
        if (newRecipes != null) {
            this.recipeList.clear();
            this.recipeList.addAll(newRecipes);
            notifyDataSetChanged();
        }
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title, prepTime, servings;
        ImageView recipeImage;

        RecipeViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.recipeTitle);
            prepTime = view.findViewById(R.id.recipePrepTime);
            servings = view.findViewById(R.id.recipeServings);
            recipeImage = view.findViewById(R.id.recipeImage);
        }
    }
}
