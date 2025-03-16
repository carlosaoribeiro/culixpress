package com.carlosribeiro.culixpress.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.carlosribeiro.culixpress.data.local.AppDatabase;
import com.carlosribeiro.culixpress.data.local.User;

import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {

    private AppDatabase db;
    public MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    public void login(String email, String password){
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().authenticate(email, password);
            authSuccess.postValue(user != null);
        });
    }

    public void register(User user){
        Executors.newSingleThreadExecutor().execute(() -> {
            db.userDao().insertUser(user);
            authSuccess.postValue(true);
        });
    }
}
