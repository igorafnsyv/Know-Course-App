package com.example.knowcourseapp.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.knowcourseapp.R;
import com.example.knowcourseapp.network.JsonUtility;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoginActivity extends Activity {

    EditText usernameView;
    EditText passwordView;
    Button submitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = findViewById(R.id.usernameInput);
        passwordView = findViewById(R.id.passwordInput);
        submitButton = findViewById(R.id.submitLogin);
        submitButton.setOnClickListener((v) -> handleLogin());


    }

    private void handleLogin() {
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = getString(R.string.server_address) + getString(R.string.retrieve_token);
        Future<String> response = executor.submit(() -> JsonUtility.postJson(url, json, this));
        try {
            Map<String, String> responseJson = gson.fromJson(response.get(), Map.class);
            System.out.println(responseJson);
            if (responseJson != null) {
                SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.token), responseJson.get("token"));
                editor.apply();
                Intent intent = new Intent(this, CourseListActivity.class);
                finish();
                startActivity(intent);
            } else  {
                Toast.makeText(this, "Incorrect login details", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

    }

}
