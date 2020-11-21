package com.example.knowcourseapp.models;

import com.example.knowcourseapp.network.NetworkUtility;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.net.ConnectException;
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
    private int averageGrade;
    private int averageWorkload;

    public Course(String code, String title, String description, List<String> prerequisites, int averageGrade, int averageWorkload) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.prerequisites = prerequisites;
        this.averageGrade = averageGrade;
        this.averageWorkload = averageWorkload;
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

    public int getAverageGrade() {
        return averageGrade;
    }

    public int getAverageWorkload() {
        return averageWorkload;
    }

    public static List<Course> getCourses() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Course> list = null;
        Future<String> res = executorService.submit(() -> NetworkUtility.readJson("http://10.0.2.2:8000/api/courses"));
        try {
            Gson gson = new Gson();
            String response = res.get();

            list = gson.fromJson(response, new TypeToken<List<Course>>(){}.getType());
        } catch (InterruptedException | ExecutionException e) {
            e.getCause().printStackTrace();
        }
        executorService.shutdown();
        return list;
    }

    public static Course getCourse(String courseCode) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Course course = null;
        String url = "http://10.0.2.2:8000/api/courses/" + courseCode + "/";
        Future<String> response = executorService.submit(() -> NetworkUtility.readJson(url));
        try {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            course = gson.fromJson(response.get(), Course.class);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        executorService.shutdown();
        return course;
    }


}
