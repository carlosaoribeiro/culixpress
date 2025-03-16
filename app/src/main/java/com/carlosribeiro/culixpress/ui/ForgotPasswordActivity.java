package com.carlosribeiro.culixpress.ui;

import android.os.Bundle;
import android.text.TextUtils;
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

        emailInput = findViewById(R.id.emailInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        resetPasswordButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String newPassword = newPasswordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(newPassword)) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.resetPassword(email, newPassword);
        });

        viewModel.passwordResetSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "E-mail não encontrado!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
