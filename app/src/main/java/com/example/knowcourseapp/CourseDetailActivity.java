package com.example.knowcourseapp;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.knowcourseapp.fragments.CoursePrerequisitesFragment;
import com.example.knowcourseapp.models.Course;
import com.example.knowcourseapp.network.JsonReader;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CourseDetailActivity extends AppCompatActivity {

    TextView courseTitleView;
    TextView courseDescriptionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_datails);
        courseTitleView = findViewById(R.id.courseTitle);
        courseDescriptionView = findViewById(R.id.courseDescription);

        Bundle extras = getIntent().getExtras();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String url = "http://10.0.2.2:8000/api/courses/" + extras.getString("courseCode");
        Future<String> response = executorService.submit(() -> JsonReader.readJson(url));
        Course course = null;
        try {
            Gson gson = new Gson();
            course = gson.fromJson(response.get(), Course.class);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        if (course != null) {
            String courseTitle = course.getCode() + " " + course.getTitle();
            courseTitleView.setText(courseTitle);
            courseDescriptionView.setText(course.getDescription());
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("prerequisites", (ArrayList<String>) course.getPrerequisites());
            CoursePrerequisitesFragment fragment = new CoursePrerequisitesFragment();
            fragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.prerequisitesFrame, fragment, "");
            transaction.commitAllowingStateLoss();
        }

    }
}
