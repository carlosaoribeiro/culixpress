package com.carlosribeiro.culixpress.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeTitle.setText(recipe.getTitle()); // Certifique-se de que Recipe tem um getTitle()
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipeList(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitle); // Verifique se existe um TextView com esse ID no layout
        }
    }
}
