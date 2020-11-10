package com.example.knowcourseapp.models;

import com.example.knowcourseapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Course {
    private String code;
    private String title;
    private String description;
    private List<String> prerequisites;

    public Course(String code, String title, String description, List<String> prerequisites) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.prerequisites = prerequisites;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public static List<Course> getCourses() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Course> list = null;
        Future<String> res = executorService.submit(() -> getJSON("http://10.0.2.2:8000/api/courses"));
        try {
            Gson gson = new Gson();
            res.get();
            list = gson.fromJson(res.get(), new TypeToken<List<Course>>(){}.getType());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return list;
    }

    private static String getJSON(String url) {
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
