package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private RecipesViewModel recipesViewModel;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private SessionManager sessionManager;
    private TextView welcomeText;
    private ImageView buttonLogout;

    private final String API_KEY = "SUA_API_KEY_AQUI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        sessionManager = new SessionManager(this);
        if (!sessionManager.isUserLoggedIn()) {
            redirectToLogin();
            return;
        }

        welcomeText = findViewById(R.id.welcomeText);
        buttonLogout = findViewById(R.id.buttonLogout);
        recyclerView = findViewById(R.id.recipesRecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                recipeAdapter = new RecipeAdapter(this, recipes);
                recyclerView.setAdapter(recipeAdapter);
            } else {
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar botão de logout
        buttonLogout.setOnClickListener(v -> {
            sessionManager.logout();
            redirectToLogin();
        });

        // Exibir o nome do usuário
        welcomeText.setText("Bem-vindo, " + sessionManager.getUserName());
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
