package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.viewmodel.RecipesViewModel;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeImage;
    private TextView recipeTitle, recipePrepTime, recipeServings, recipeInstructions;
    private Button buttonShare;
    private RecipesViewModel recipesViewModel;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // 🔹 Inicializa os elementos da UI
        recipeImage = findViewById(R.id.detailRecipeImage);
        recipeTitle = findViewById(R.id.detailRecipeTitle);
        recipePrepTime = findViewById(R.id.detailRecipePrepTime);
        recipeServings = findViewById(R.id.detailRecipeServings);
        recipeInstructions = findViewById(R.id.detailRecipeInstructions);
        buttonShare = findViewById(R.id.buttonShare);

        // 🔹 Inicializa o ViewModel
        recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        // 🔹 Recebe os dados da Intent
        Intent intent = getIntent();
        if (intent == null) {
            Log.e("RecipeDetailActivity", "❌ Erro: Intent está nula.");
            finish();
            return;
        }

        // 📌 Captura os dados passados da lista de receitas
        recipeId = intent.getIntExtra("recipeId", -1);
        String title = intent.getStringExtra("title");
        String imageUrl = intent.getStringExtra("imageUrl");
        int prepTime = intent.getIntExtra("prepTime", -1); // 🔹 Valor padrão -1 para verificar
        int servings = intent.getIntExtra("servings", -1);
        String instructions = intent.getStringExtra("instructions");

        // 📌 Logs para depuração
        Log.d("RecipeDetailActivity", "📌 Dados recebidos:");
        Log.d("RecipeDetailActivity", "🔹 ID: " + recipeId);
        Log.d("RecipeDetailActivity", "🔹 Título: " + title);
        Log.d("RecipeDetailActivity", "🔹 Tempo de preparo: " + prepTime);
        Log.d("RecipeDetailActivity", "🔹 Porções: " + servings);
        Log.d("RecipeDetailActivity", "🔹 Instruções: " + instructions);

        // 🔹 Preenche os campos de texto
        recipeTitle.setText(title != null ? title : "Receita desconhecida");
        recipePrepTime.setText(prepTime > 0 ? "⏳ Tempo de preparo: " + prepTime + " min" : "⏳ Tempo de preparo não informado");
        recipeServings.setText(servings > 0 ? "🍽️ Porções: " + servings : "🍽️ Porções não informadas");

        // 🔹 Carrega a imagem com Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(recipeImage);

        // 🔹 Exibe as instruções ou busca na API
        if (instructions != null && !instructions.isEmpty() && !instructions.equals("Instruções não disponíveis.")) {
            recipeInstructions.setText(instructions);
        } else if (recipeId != -1) {
            Log.d("RecipeDetailActivity", "📜 Buscando instruções da API para ID: " + recipeId);
            recipeInstructions.setText("📜 Carregando instruções...");
            fetchRecipeInstructions();
        } else {
            recipeInstructions.setText("Instruções não disponíveis.");
        }

        // 🔹 Configura o botão de compartilhamento
        buttonShare.setOnClickListener(view -> shareRecipe());
    }

    // 🔹 Método para buscar instruções da receita na API
    private void fetchRecipeInstructions() {
        if (recipeId == -1) {
            Log.e("RecipeDetailActivity", "❌ ID da receita inválido.");
            return;
        }

        recipesViewModel.getRecipeInstructions(recipeId).observe(this, instructions -> {
            if (instructions != null && !instructions.isEmpty() && !instructions.equals("Instruções não disponíveis.")) {
                recipeInstructions.setText(instructions);
                Log.d("RecipeDetailActivity", "📜 Instruções recebidas: " + instructions);
            } else {
                recipeInstructions.setText("Instruções não disponíveis.");
                Log.e("RecipeDetailActivity", "❌ Nenhuma instrução foi recebida.");
            }
        });
    }

    // 🔹 Método para compartilhar a receita
    private void shareRecipe() {
        String title = recipeTitle.getText().toString();
        String prepTime = recipePrepTime.getText().toString();
        String servings = recipeServings.getText().toString();
        String instructions = recipeInstructions.getText().toString();

        // 🔹 Criando a mensagem para compartilhar
        String shareMessage = "🍽️ *" + title + "*\n\n"
                + prepTime + "\n"
                + servings + "\n\n"
                + "📜 Instruções:\n" + instructions + "\n\n"
                + "Enviado via *CuliXpress*";

        // 🔹 Criando a Intent de Compartilhamento
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Confira esta receita no CuliXpress!");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        // 🔹 Inicia a Intent de compartilhamento
        startActivity(Intent.createChooser(shareIntent, "Compartilhar Receita via"));
    }
}
