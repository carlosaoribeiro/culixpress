package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.ui.adapter.RecipeAdapter;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipesViewModel recipesViewModel;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;

    private final String API_KEY = "95c67c60762f4590a95269477adb067f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_main);

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

// 🔥 Certifique-se de que está inicializando corretamente o Adapter
        recipeAdapter = new RecipeAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(recipeAdapter);

// 🔥 Pegando as receitas e atualizando o Adapter
        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);
        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                Log.d("MainActivity", "🔄 Receitas carregadas: " + recipes.size());
                recipeAdapter.setRecipeList(recipes);
            } else {
                Log.e("MainActivity", "❌ Nenhuma receita carregada!");
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
