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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes != null ? recipes : new ArrayList<>();
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

        holder.recipeTitle.setText(recipe.getTitle());

        // ✅ Verifica se a URL está sendo carregada no Logcat
        System.out.println("🔍 Carregando imagem: " + recipe.getImageUrl());

        // ✅ Glide para carregar a imagem com placeholder e erro
        Glide.with(holder.itemView.getContext())
                .load(recipe.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Mantém cache para performance
                .placeholder(R.drawable.placeholder_image) // Imagem de carregamento
                .error(R.drawable.error_image) // Imagem de erro
                .into(holder.recipeImage);

        // ✅ Adicionando clique para abrir detalhes da receita
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId()); // Passando o ID da receita
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    // ✅ Método para atualizar a lista de receitas e notificar o RecyclerView
    public void setRecipeList(List<Recipe> newRecipes) {
        this.recipes.clear();
        this.recipes.addAll(newRecipes);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
        }
    }
}
