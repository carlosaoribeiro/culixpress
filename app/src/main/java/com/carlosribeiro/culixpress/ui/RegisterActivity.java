package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.carlosribeiro.culixpress.data.local.User;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;



import com.carlosribeiro.culixpress.R;

import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button buttonRegister;
    private TextView textToLogin;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.registerUsername);
        emailEditText = findViewById(R.id.registerEmail);
        passwordEditText = findViewById(R.id.registerPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        TextView textToLogin = findViewById(R.id.textToLogin);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        buttonRegister.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User(username, email, password);
                viewModel.register(user);

                viewModel.getAuthSuccess().observe(this, success -> {  // ✅ Agora está dentro do bloco correto
                    if (success) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Volta automaticamente para o login
                    }
                });
            }
        });

                // ✅ Agora o `setOnClickListener` está fechado corretamente!
                textToLogin.setOnClickListener(v -> finish());
    }
}
