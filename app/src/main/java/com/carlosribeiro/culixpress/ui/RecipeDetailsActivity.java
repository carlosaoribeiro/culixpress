package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;


public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipesViewModel viewModel;
    private ImageView recipeImage;
    private TextView recipeTitle, recipeInstructions;

    private final String API_KEY = "95c67c60762f4590a95269477adb067f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeImage = findViewById(R.id.detailRecipeImage);
        recipeTitle = findViewById(R.id.detailRecipeTitle);
        recipeInstructions = findViewById(R.id.detailRecipeInstructions);

        // Pegando ID correto enviado pelo Adapter
        int recipeId = getIntent().getIntExtra("recipe_id", 0);

        // Inicialize o ViewModel
        viewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // Buscar receita detalhada usando ID
        viewModel.getRecipeDetail(recipeId, API_KEY).observe(this, recipeDetail -> {
            if (recipeDetail != null) {
                recipeTitle.setText(recipeDetail.getTitle());
                recipeInstructions.setText(recipeDetail.getInstructions());

                Glide.with(this)
                        .load(recipeDetail.getImage())
                        .into(recipeImage);
            }
        });
    }
}
