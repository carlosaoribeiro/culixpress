package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText loginEmail, loginPassword;
    private MaterialButton buttonLogin;
    private ProgressBar progressBar;
    private AuthViewModel viewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Log.d("LoginActivity", "Tela de login carregada!");

        // Esconder a Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Inicializando os elementos do layout
        TextInputLayout loginEmailLayout = findViewById(R.id.loginEmailLayout);
        TextInputLayout loginPasswordLayout = findViewById(R.id.loginPasswordLayout);
        loginEmail = loginEmailLayout != null ? (TextInputEditText) loginEmailLayout.getEditText() : null;
        loginPassword = loginPasswordLayout != null ? (TextInputEditText) loginPasswordLayout.getEditText() : null;

        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        // ✅ Verifica se o usuário já está logado antes de mostrar a tela de login
        if (sessionManager.isUserLoggedIn() && sessionManager.getUserEmail() != null) {
            Log.d("LoginActivity", "Usuário já logado! Redirecionando para MainActivity...");
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // Clique no botão de login
        buttonLogin.setOnClickListener(view -> {
            if (loginEmail == null || loginPassword == null) {
                Toast.makeText(this, "Erro ao carregar os campos!", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Erro ao acessar os campos de entrada.");
                return;
            }

            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if (email.isEmpty()) {
                loginEmail.setError("Digite seu e-mail!");
                return;
            }

            if (pass.isEmpty()) {
                loginPassword.setError("Digite sua senha!");
                return;
            }

            Log.d("LoginActivity", "Enviando credenciais para login: " + email);

            // Exibe o ProgressBar e desativa o botão
            progressBar.setVisibility(View.VISIBLE);
            buttonLogin.setEnabled(false);

            viewModel.login(email, pass);
        });

        // ✅ Observa sucesso ou falha no login
        viewModel.getAuthSuccess().observe(this, message -> {
            progressBar.setVisibility(View.GONE);
            buttonLogin.setEnabled(true);

            if (message.equals("Login bem-sucedido!")) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", "Usuário autenticado com sucesso!");

                // ✅ Buscar dados do usuário apenas se o login for bem-sucedido
                viewModel.getUserData().observe(this, user -> {
                    if (user != null) {
                        String email = user.getEmail();
                        String name = user.getUsername();

                        Log.d("LoginActivity", "Dados do usuário carregados: " + name + " (" + email + ")");

                        // Salvar sessão do usuário
                        sessionManager.saveUserSession(email, name);

                        // Redirecionar para MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Erro no login: " + message);
            }
        });

        // Esqueci minha senha
        findViewById(R.id.textForgotPassword).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        // Redirecionar para tela de cadastro
        findViewById(R.id.textToRegister).setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
