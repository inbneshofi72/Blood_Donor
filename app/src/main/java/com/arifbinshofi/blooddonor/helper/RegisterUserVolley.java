package com.arifbinshofi.blooddonor.helper;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class RegisterUserVolley {
    private static final String REGISTER_URL = "https://ibnejahid.xyz/bloodDonor/register.php";
    private static final String SECRET_KEY = "0175526800812345";

    public static void registerUser(Context context, String name, String phone, String email, String password, String image, UserPreferences userPreferences) {

        String encryptedName = encryptData(name);
        String encryptedPhone = encryptData(phone);
        String encryptedEmail = encryptData(email);
        String encryptedPassword = encryptData(password);
        String encryptedKey = encryptData("Jobayer121415");

        JSONObject request = new JSONObject();
        try {
            request.put("name", encryptedName);
            request.put("phone", encryptedPhone);
            request.put("gmail", encryptedEmail);
            request.put("password", encryptedPassword);
            request.put("image", image);  // Add the image in base64
            request.put("key", encryptedKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the registration request
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");

                            if (status.equals("success")) {
                                userPreferences.saveUserInfo( phone, password);
                                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Registration failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    // Method for encrypting data
    private static String encryptData(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptedData, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
