package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button buttonLogin;
    private TextView textToRegister, textForgotPassword;
    private AuthViewModel viewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Esconder a Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Inicializando os elementos do layout
        TextInputLayout loginEmailLayout = findViewById(R.id.loginEmailLayout);
        TextInputLayout loginPasswordLayout = findViewById(R.id.loginPasswordLayout);

        loginEmail = loginEmailLayout != null ? loginEmailLayout.getEditText() : null;
        loginPassword = loginPasswordLayout != null ? loginPasswordLayout.getEditText() : null;

        buttonLogin = findViewById(R.id.buttonLogin);
        textToRegister = findViewById(R.id.textToRegister);
        textForgotPassword = findViewById(R.id.textForgotPassword);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        // Se o usuário já estiver logado, ir direto para a MainActivity
        if (sessionManager.isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        buttonLogin.setOnClickListener(view -> {
            if (loginEmail == null || loginPassword == null) {
                Toast.makeText(this, "Erro ao carregar os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.login(email, pass);
            }
        });

        textForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        viewModel.getAuthSuccess().observe(this, success -> {
            System.out.println("🔍 Login retornou: " + success);

            if (success) {
                Toast.makeText(this, "Login bem-sucedido! Abrindo MainActivity...", Toast.LENGTH_SHORT).show();

                String email = loginEmail != null ? loginEmail.getText().toString().trim() : "";
                String name = "Usuário Padrão";

                sessionManager.saveUserSession(email, name);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });



        textToRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
