package com.carlosribeiro.culixpress.ui;

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

        if (recipe == null) {
            Log.e("RecipeAdapter", "Erro: Receita é nula!");
            return;
        }


        Log.d("RecipeAdapter", "Renderizando item: " + recipe.getTitle());

        holder.title.setText(recipe.getTitle());

        Glide.with(holder.itemView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            Log.d("RecipeAdapter", "Item clicado: " + recipe.getTitle() + " | ID: " + recipe.getId());
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("RECIPE", recipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> newRecipes) {
        this.recipeList.clear();
        this.recipeList.addAll(newRecipes);
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        RecipeViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.recipeTitle);
            image = view.findViewById(R.id.recipeImage);
        }
    }
}
