package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.ui.adapter.RecipeAdapter;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private TextView welcomeText;
    private RecyclerView recyclerViewRecipes;
    private RecipeAdapter recipeAdapter;
    private RecipesViewModel recipesViewModel;
    private SessionManager sessionManager;

    private static final String API_KEY = "95c67c60762f4590a95269477adb067f"; // 🔑 Substitua pela sua chave de API válida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_main);

        // 🔥 Inicializando os componentes da UI
        logoutButton = findViewById(R.id.buttonLogout);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        welcomeText = findViewById(R.id.welcomeText);
        sessionManager = new SessionManager(this);

        // ✅ Obtendo o nome do usuário salvo na sessão
        String userName = sessionManager.getUserName();
        welcomeText.setText("Olá, " + (userName != null ? userName : "Usuário") + "!");

        // ✅ Configuração do RecyclerView
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

        // 🛠 Corrigido erro no RecipeAdapter → Agora passamos um contexto e uma lista vazia
        recipeAdapter = new RecipeAdapter(this, new ArrayList<>());
        recyclerViewRecipes.setAdapter(recipeAdapter);

        // ✅ Inicializa a ViewModel corretamente
        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // 🔥 Garante que a API seja chamada corretamente antes da observação
        recipesViewModel.loadRecipes(API_KEY);

        // ✅ Observa mudanças nos dados das receitas
        recipesViewModel.getRecipes().observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                Log.d("MainActivity", "Receitas carregadas: " + recipes.size());
                recipeAdapter.updateList(recipes); // ✅ Atualiza os dados no Adapter
            } else {
                Log.e("MainActivity", "Nenhuma receita carregada!");
            }
        });

        // ✅ Logout do usuário
        logoutButton.setOnClickListener(view -> {
            sessionManager.logoutUser();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
