package com.example.knowcourseapp.models;

import android.widget.TextView;

import com.example.knowcourseapp.network.JsonReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CourseReview {
    private String author;
    private String dateCreated;
    private String rating;
    private String yearTaken;
    private String subclass;

    public CourseReview(String author, String dateCreated, String rating, String yearTaken, String subclass) {
        this.author = author;
        this.dateCreated = dateCreated;
        this.rating = rating;
        this.yearTaken = yearTaken;
        this.subclass = subclass;
    }


    public String getAuthor() {
        return author;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getRating() {
        return rating;
    }

    public String getYearTaken() {
        return yearTaken;
    }

    public String getSubclass() {
        return subclass;
    }

    public static List<CourseReview> getCourseReview(String courseCode) {
        List<CourseReview> courseReviews = null;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        String url = "http://10.0.2.2:8000/api/courses/" + courseCode + "/reviews/";
        System.out.println(url);
        Future<String> result = executor.submit(() -> JsonReader.readJson(url));
        try {
            Gson gson = new Gson();
            System.out.println(result.get());
            courseReviews = gson.fromJson(result.get(), new TypeToken<List<CourseReview>>(){}.getType());
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return courseReviews;
    }
}
