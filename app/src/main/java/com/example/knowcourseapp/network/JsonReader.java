package com.example.knowcourseapp.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonReader {

    private JsonReader() {}

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

        } catch (Exception ex) {
            ex.printStackTrace();
            return "failed to connect";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }
}
