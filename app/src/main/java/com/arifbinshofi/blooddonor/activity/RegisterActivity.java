package com.arifbinshofi.blooddonor.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arifbinshofi.blooddonor.databinding.ActivityRegisterBinding;
import com.arifbinshofi.blooddonor.helper.RegisterUserVolley;
import com.arifbinshofi.blooddonor.helper.UserPreferences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityRegisterBinding binding;
    private UserPreferences userPreferences;
    private String encodedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userPreferences = new UserPreferences(this);

        binding.btnRegister.setOnClickListener(v -> registerUser());

        binding.loginStarted.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        binding.btnSelectImage.setOnClickListener(v -> openImageChooser());
    }

    private void registerUser() {
        String name = binding.editTextUserName.getText().toString();
        String phone = binding.editTextPhoneNumber.getText().toString();
        String email = binding.editTextGmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || encodedImage == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
        } else {
            RegisterUserVolley.registerUser(this, name, phone, email, password, encodedImage, userPreferences);
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                encodedImage = encodeImage(bitmap);
                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
