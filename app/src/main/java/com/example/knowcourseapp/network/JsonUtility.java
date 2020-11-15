package com.example.knowcourseapp.network;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JsonUtility {

    private JsonUtility() {}

    public static String readJson(String url) {

        HttpURLConnection connection = null;
        String response = "";
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setInstanceFollowRedirects(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder jsonSourceBuilder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                jsonSourceBuilder.append(line);
                jsonSourceBuilder.append(System.lineSeparator());
            }
            response = jsonSourceBuilder.toString();
            reader.close();

        } catch (ConnectException ex) {
            ex.printStackTrace();
            return "failed to connect";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    public static String postJson(String url, String json, Context context) {
        HttpURLConnection connection;
        String response = "";
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            AccountManager manager = AccountManager.get(context);
            Account[] accounts = manager.getAccountsByType("com.example.knowcourseapp");
            if (accounts.length > 0) {
                Account account = accounts[0];
                String username = account.name;
                String password = manager.getPassword(account);
                String auth = username + ":" + password;
                byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
                String authHeaderValue = "Basic " + new String(encodeAuth);
                connection.setRequestProperty("Authorization", authHeaderValue);

            }
            connection.setDoInput(true);
            connection.setDoOutput(true);


            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            response = String.valueOf(connection.getResponseCode());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
