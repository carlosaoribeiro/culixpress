package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;

public class RecipeDetailActivity extends AppCompatActivity {

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
        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId == -1) {
            Toast.makeText(this, "Erro ao carregar a receita", Toast.LENGTH_SHORT).show();
            recipeTitle.setText("Receita não encontrada");
            return;
        }

        // Inicialize o ViewModel
        viewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // Buscar receita detalhada usando ID
        viewModel.getRecipeDetail(recipeId, API_KEY).observe(this, recipeDetail -> {
            if (recipeDetail != null) {
                if (recipeDetail.getTitle() != null) {
                    recipeTitle.setText(recipeDetail.getTitle());
                } else {
                    recipeTitle.setText("Título indisponível");
                }

                if (recipeDetail.getInstructions() != null) {
                    recipeInstructions.setText(recipeDetail.getInstructions());
                } else {
                    recipeInstructions.setText("Instruções não disponíveis.");
                }

                if (recipeDetail.getImage() != null && !recipeDetail.getImage().isEmpty()) {
                    Glide.with(this)
                            .load(recipeDetail.getImage())
                            .into(recipeImage);
                } else {
                    recipeImage.setImageResource(R.drawable.placeholder_image); // Imagem padrão se não houver
                }
            } else {
                Toast.makeText(this, "Erro ao carregar detalhes da receita", Toast.LENGTH_SHORT).show();
                Log.e("RecipeDetailActivity", "Receita retornou nula.");
            }
        });
    }
}
