package com.arifbinshofi.blooddonor.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserInfo( String phone, String password) {
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public String getKeyName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getKeyPhone() {
        return sharedPreferences.getString(KEY_PHONE, "");
    }

    public String getKeyPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }
}
