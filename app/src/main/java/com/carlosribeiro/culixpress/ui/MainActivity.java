package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.ui.adapters.RecipeAdapter;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private RecipesViewModel recipesViewModel;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;

    private final String API_KEY = "95c67c60762f4590a95269477adb067f"; // Insira sua chave da API aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        // Se o usuário não estiver logado, redireciona para Login
        if (!sessionManager.isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);

        // Inicializar ViewModel
        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // Chamar API para buscar receitas
        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                recipeAdapter.setRecipeList(recipes);
            } else {
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });

        // Botão de Logout
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> {
            sessionManager.logoutUser();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
