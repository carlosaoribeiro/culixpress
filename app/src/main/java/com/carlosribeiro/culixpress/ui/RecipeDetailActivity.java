package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeImage;
    private TextView recipeTitle, recipeInstructions;
    private Button buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Inicializar Views
        recipeImage = findViewById(R.id.detailRecipeImage);
        recipeTitle = findViewById(R.id.detailRecipeTitle);
        recipeInstructions = findViewById(R.id.detailRecipeInstructions);
        buttonShare = findViewById(R.id.buttonShare);

        // Receber dados da Intent
        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        String recipeTitleText = getIntent().getStringExtra("RECIPE_TITLE");
        String recipeImageUrl = getIntent().getStringExtra("RECIPE_IMAGE");
        String recipeInstructionsText = getIntent().getStringExtra("RECIPE_INSTRUCTIONS");

        if (recipeId == -1 || recipeTitleText == null || recipeImageUrl == null) {
            Log.e("RecipeDetailActivity", "❌ Erro: Dados da receita estão NULL ou inválidos!");
            Toast.makeText(this, "Erro ao carregar a receita", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("RecipeDetailActivity", "🟢 Receita carregada com sucesso!");
        Log.d("RecipeDetailActivity", "🔹 ID: " + recipeId);
        Log.d("RecipeDetailActivity", "🔹 Título: " + recipeTitleText);
        Log.d("RecipeDetailActivity", "🔹 Imagem: " + recipeImageUrl);
        Log.d("RecipeDetailActivity", "🔹 Instruções: " + recipeInstructionsText);

        // Exibir os dados na UI
        recipeTitle.setText(recipeTitleText);

        if (recipeInstructionsText == null || recipeInstructionsText.isEmpty()) {
            recipeInstructions.setText("Instruções não disponíveis.");
        } else {
            recipeInstructions.setText(recipeInstructionsText);
        }

        Glide.with(this)
                .load(recipeImageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(recipeImage);

        buttonShare.setOnClickListener(view -> shareRecipe(recipeTitleText, recipeInstructionsText));
    }

    private void shareRecipe(String recipeTitleText, String recipeInstructionsText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Confira esta receita!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, recipeTitleText + "\n\n" + recipeInstructionsText);

        startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
    }
}


