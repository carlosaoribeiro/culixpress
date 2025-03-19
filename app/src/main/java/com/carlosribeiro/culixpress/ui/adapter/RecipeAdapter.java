package com.carlosribeiro.culixpress.ui.adapter;

import android.app.Activity;
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
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private final List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = new ArrayList<>(recipeList); // Garante imutabilidade
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        // ✅ Define os dados na view
        holder.title.setText(recipe.getTitle());

        // ✅ Garante valores válidos para tempo de preparo e porções
        int prepTime = recipe.getPreparationTime();
        int servings = recipe.getServings();

        holder.prepTime.setText("⏳ Tempo de preparo: " + (prepTime > 0 ? prepTime + " min" : "Não informado"));
        holder.servings.setText("🍽️ Porções: " + (servings > 0 ? servings : "Não informado"));

        // ✅ Carrega a imagem com Glide
        Glide.with(context)
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.recipeImage);

        // ✅ Evento de clique para abrir os detalhes
        holder.itemView.setOnClickListener(view -> {
            Log.d("RecipeAdapter", "🍽️ Clicou na receita: " + recipe.getTitle());
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("imageUrl", recipe.getImageUrl());
            intent.putExtra("prepTime", prepTime);
            intent.putExtra("servings", servings);
            intent.putExtra("instructions", recipe.getFormattedInstructions());

            // ✅ Garante que será aberto corretamente, mesmo fora de uma activity
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title, prepTime, servings;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipeTitle);
            prepTime = itemView.findViewById(R.id.recipePrepTime);
            servings = itemView.findViewById(R.id.recipeServings);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }

    // ✅ Método atualizado para atualizar a lista de forma correta
    public void updateList(List<Recipe> newRecipes) {
        recipeList.clear();
        recipeList.addAll(newRecipes);
        notifyDataSetChanged();
    }
}
