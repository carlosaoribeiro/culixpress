package com.carlosribeiro.culixpress.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User authenticate(String email, String password);  // 🔥 Agora retorna um `User` diretamente



    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    LiveData<User> getUserByEmail(String email);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    int updatePassword(String email, String newPassword);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int checkEmailExists(String email);  // ✅ Verifica se o email existe antes de atualizar senha
}
