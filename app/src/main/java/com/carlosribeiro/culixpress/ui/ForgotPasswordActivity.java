package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.carlosribeiro.culixpress.R;
import com.carlosribeiro.culixpress.viewmodel.AuthViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput, newPasswordInput;
    private Button resetPasswordButton;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Inicializa elementos do layout
        emailInput = findViewById(R.id.emailInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Clique no botão de redefinição de senha
        resetPasswordButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String newPassword = newPasswordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Digite seu e-mail!");
                return;
            }

            if (TextUtils.isEmpty(newPassword)) {
                newPasswordInput.setError("Digite uma nova senha!");
                return;
            }

            Log.d("ForgotPasswordActivity", "Enviando solicitação de redefinição para ViewModel");
            viewModel.resetPassword(email, newPassword);
        });

        // Observando a resposta do ViewModel
        viewModel.getPasswordResetSuccess().observe(this, message -> {
            if (message.equals("Senha redefinida com sucesso!")) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.d("ForgotPasswordActivity", "Senha redefinida com sucesso!");
                finish(); // Fecha a tela após redefinir a senha
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Log.e("ForgotPasswordActivity", "Erro ao redefinir senha: " + message);
            }
        });
    }
}
