package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.model.Recipe;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    private RecipesViewModel recipesViewModel;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;

    @Override
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        sessionManager = new SessionManager(this);



        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        recipesViewModel.getEasyRecipes(API_KEY).observe(this, recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
            } else {
                Toast.makeText(this, "Nenhuma receita encontrada!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
