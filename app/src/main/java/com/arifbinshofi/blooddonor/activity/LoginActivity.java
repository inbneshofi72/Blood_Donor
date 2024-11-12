package com.arifbinshofi.blooddonor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.arifbinshofi.blooddonor.R;
import com.arifbinshofi.blooddonor.databinding.ActivityLoginBinding;
import com.arifbinshofi.blooddonor.helper.LoginUserVolley;
import com.arifbinshofi.blooddonor.helper.UserPreferences;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreferences = new UserPreferences(this);

        binding.btnLogin.setOnClickListener(v -> loginUser());

        if (userPreferences.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.registerStarted.setOnClickListener(v->{
            startActivity(new Intent(this,RegisterActivity.class));
        });

    }

    private void loginUser() {
        String phone = binding.editTextPhoneNumber.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        } else {
            LoginUserVolley.loginUser(this, phone, password, userPreferences);
        }
    }
}