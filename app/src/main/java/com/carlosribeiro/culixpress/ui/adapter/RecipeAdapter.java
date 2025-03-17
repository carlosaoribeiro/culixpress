package com.carlosribeiro.culixpress.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.carlosribeiro.culixpress.ui.RecipeDetailActivity;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        if (recipe == null) {
            Log.e("RecipeAdapter", "❌ Erro: Receita é nula!");
            return;
        }

        Log.d("RecipeAdapter", "🔄 Renderizando item: " + recipe.getTitle());

        holder.recipeTitle.setText(recipe.getTitle());

        Glide.with(holder.itemView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.recipeImage);

        Log.d("RecipeAdapter", "🟡 Preparando clique para: " + recipe.getTitle());

        holder.itemView.setOnClickListener(view -> {
            Log.d("RecipeAdapter", "🟢 Item clicado: " + recipe.getTitle());

            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", recipe.getId());
            intent.putExtra("RECIPE_TITLE", recipe.getTitle());
            intent.putExtra("RECIPE_IMAGE", recipe.getImageUrl());

            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipeList(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        ImageView recipeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }
}
