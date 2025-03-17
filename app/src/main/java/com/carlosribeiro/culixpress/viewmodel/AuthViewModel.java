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
    private final MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();
    private final MutableLiveData<User> userData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> passwordResetSuccess = new MutableLiveData<>();

    public LiveData<Boolean> getAuthSuccess() {
        return authSuccess;
    }

    public LiveData<User> getUserData() {
        return userData;
    }

    public LiveData<Boolean> getPasswordResetSuccess() {
        return passwordResetSuccess;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    public void login(String email, String password) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().authenticate(email, password);

            if (user != null) {
                userData.postValue(user);
                authSuccess.postValue(true);
            } else {
                authSuccess.postValue(false);
            }
        });
    }

    public LiveData<Boolean> register(User user) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(() -> {
            db.userDao().insertUser(user);
            result.postValue(true);
        });

        return result;
    }

    public void resetPassword(String email, String newPassword) {
        Executors.newSingleThreadExecutor().execute(() -> {
            int updatedRows = db.userDao().updatePassword(email, newPassword);
            passwordResetSuccess.postValue(updatedRows > 0);
        });
    }
}
