package com.carlosribeiro.culixpress.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.carlosribeiro.culixpress.data.local.AppDatabase;
import com.carlosribeiro.culixpress.data.local.User;

import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {

    private final AppDatabase db;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(); // 🔹 Estado de carregamento
    private final MutableLiveData<String> authSuccess = new MutableLiveData<>();
    private final MutableLiveData<User> userData = new MutableLiveData<>();
    private final MutableLiveData<String> passwordResetSuccess = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getAuthSuccess() {
        return authSuccess;
    }

    public LiveData<User> getUserData() {
        return userData;
    }

    public LiveData<String> getPasswordResetSuccess() {
        return passwordResetSuccess;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    // ✅ Método de login com loading
    public void login(String email, String password) {
        isLoading.postValue(true); // 🔹 Inicia o carregamento
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().authenticate(email, password);

            if (user != null) {
                userData.postValue(user);
                authSuccess.postValue("Login bem-sucedido!");
            } else {
                authSuccess.postValue("Email ou senha incorretos!");
            }

            isLoading.postValue(false); // 🔹 Finaliza o carregamento
        });
    }


    // ✅ Método de registro corrigido - agora verifica se o usuário já existe
    public void register(User user) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Verificar se o email já está cadastrado
                User existingUser = db.userDao().getUserByEmail(user.getEmail()).getValue();

                if (existingUser != null) {
                    authSuccess.postValue("Este email já está cadastrado!");
                    return;
                }

                // Inserir novo usuário
                db.userDao().insertUser(user);
                authSuccess.postValue("Cadastro realizado com sucesso!");

            } catch (Exception e) {
                authSuccess.postValue("Erro ao cadastrar usuário: " + e.getMessage());
            }
        });
    }



    // ✅ Método para redefinir senha corrigido
    public void resetPassword(String email, String newPassword) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                int updatedRows = db.userDao().updatePassword(email, newPassword);
                if (updatedRows > 0) {
                    passwordResetSuccess.postValue("Senha redefinida com sucesso!");
                } else {
                    passwordResetSuccess.postValue("Erro: Email não encontrado.");
                }
            } catch (Exception e) {
                passwordResetSuccess.postValue("Erro ao redefinir senha: " + e.getMessage());
            }
        });
    }

}
