package com.carlosribeiro.culixpress.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserSession(String email, String name) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_NAME, "Usuário");
    }

    public boolean isUserLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // ✅ Método corrigido para fazer logout corretamente
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
