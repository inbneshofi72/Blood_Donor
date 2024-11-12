package com.arifbinshofi.blooddonor.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arifbinshofi.blooddonor.activity.LoginActivity;
import com.arifbinshofi.blooddonor.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginUserVolley {

    private static final String SECRET_KEY = "0175526800812345";
    private static final String LOGIN_URL = "https://ibnejahid.xyz/bloodDonor/login.php";

    public static void loginUser(Context context, String phone, String password, UserPreferences userPreferences) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("phone", encryptData(phone));
            requestBody.put("password", encryptData(password));
            requestBody.put("key", encryptData("Jobayer121415"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOGIN_URL, requestBody,
                response -> {
                    try {
                        String status = response.getString("status");
                        if ("success".equals(status)) {
                            // Save user info in SharedPreferences
                            //String userName = response.getString("name");
                            String userPhone = response.getString("phone");
                            String userPassword = response.getString("password");

                            userPreferences.saveUserInfo( userPhone, userPassword);
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show();
                            // Redirect to home screen after login
                            context.startActivity(new Intent(context, MainActivity.class));
                            ((LoginActivity) context).finish();
                        } else {
                            Toast.makeText(context, "Login failed! Invalid credentials.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonObjectRequest);
    }

    // Encrypt data (e.g., phone, password)
    private static String encryptData(String text) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
