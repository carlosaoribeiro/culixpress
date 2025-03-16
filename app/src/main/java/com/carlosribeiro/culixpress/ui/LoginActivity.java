package com.carlosribeiro.culixpress.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.carlosribeiro.culixpress.ui.MainActivity;



import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.SessionManager;
import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button buttonLogin;
    private TextView textToRegister;
    private AuthViewModel viewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textToRegister = findViewById(R.id.textToRegister);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        // Se o usuário já estiver logado, ir direto para a MainActivity
        if (sessionManager.isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        buttonLogin.setOnClickListener(view -> {
            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if(email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.login(email, pass);
            }
        });

        viewModel.authSuccess.observe(this, success -> {
            if(success){
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                sessionManager.saveUserSession(loginEmail.getText().toString().trim());
                startActivity(new Intent(this, MainActivity.class));
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
