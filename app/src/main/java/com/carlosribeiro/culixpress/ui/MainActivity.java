package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private RecipesViewModel recipesViewModel;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;

    private final String API_KEY = "95c67c60762f4590a95269477adb067f"; // Chave da API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_main);

        TextView welcomeText = findViewById(R.id.welcomeText);

        sessionManager = new SessionManager(this);

        // 🔴 Se o usuário não estiver logado, redireciona para Login
        if (!sessionManager.isUserLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // ✅ Recuperar nome do usuário salvo no SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nomeUsuario = prefs.getString("username", "Usuário");

        // ✅ Definir texto de boas-vindas
        welcomeText.setText("Bem-vindo ao CuliXpress, " + (nomeUsuario != null ? nomeUsuario : "Usuário") + "!");

        // ✅ Configurar RecyclerView corretamente
        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔥 Inicializando RecipeAdapter com um ArrayList vazio
        recipeAdapter = new RecipeAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(recipeAdapter);

        // 🔥 Inicializar ViewModel
        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // 🔄 Buscar receitas da API e atualizar Adapter
        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                for (Recipe recipe : recipes) {
                    System.out.println("📷 Imagem da Receita: " + recipe.getImageUrl());
                }
                recipeAdapter.setRecipeList(recipes);
            } else {
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔴 Configurar botão de logout corretamente
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> {
            sessionManager.logoutUser();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
