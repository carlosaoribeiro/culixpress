package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
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

public class RecipesActivity extends AppCompatActivity {

    private RecipesViewModel recipesViewModel;
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private final String API_KEY = "95c67c60762f4590a95269477adb067f";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        sessionManager = new SessionManager(this);

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecipeAdapter(new ArrayList<>()); // Instância com lista vazia
        recyclerView.setAdapter(adapter);

        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                adapter.setRecipeList(recipes);
            } else {
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
