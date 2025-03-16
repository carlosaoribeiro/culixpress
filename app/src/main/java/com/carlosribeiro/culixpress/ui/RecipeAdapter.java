package com.carlosribeiro.culixpress.ui.adapters;

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
import com.carlosribeiro.culixpress.ui.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();

    public void setRecipeList(List<Recipe> recipes) {
        this.recipeList = recipes;
        notifyDataSetChanged();
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

        Glide.with(holder.itemView.getContext())
                .load(recipe.getImage())
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RecipeDetailsActivity.class);
            intent.putExtra("recipe_id", recipe.getId()); // Passa o ID da receita
            intent.putExtra("recipe_title", recipe.getTitle());
            intent.putExtra("recipe_image", recipe.getImage());
            view.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
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
