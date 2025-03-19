package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.data.local.User;
import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername, registerEmail, registerPassword;
    private Button onRegister;
    private TextView textToLogin;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Culixpress);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        // Inicializando os elementos da interface
        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        onRegister = findViewById(R.id.btnRegister);
        textToLogin = findViewById(R.id.textToLogin);

        // Inicializando ViewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Listener para o botão de registro
        onRegister.setOnClickListener(view -> {
            Log.d("RegisterActivity", "Botão Cadastrar clicado"); // Debug
            registerUser();
        });

        // Listener para voltar ao Login
        textToLogin.setOnClickListener(v -> finish());

        // Observando resultado do registro
        viewModel.getAuthSuccess().observe(this, message -> {
            if (message.equals("Cadastro realizado com sucesso!")) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.d("RegisterActivity", "Usuário cadastrado com sucesso!");
                finish();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.e("RegisterActivity", "Erro ao cadastrar: " + message);
            }
        });
    }

    private void registerUser() {
        String username = registerUsername.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        if (username.isEmpty()) {
            registerUsername.setError("Nome obrigatório!");
            return;
        }

        if (email.isEmpty()) {
            registerEmail.setError("E-mail obrigatório!");
            return;
        }

        if (password.isEmpty()) {
            registerPassword.setError("Senha obrigatória!");
            return;
        }

        Log.d("RegisterActivity", "Enviando usuário para ViewModel");
        User user = new User(username, email, password);
        viewModel.register(user);
    }
}
