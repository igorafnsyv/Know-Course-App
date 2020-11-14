package com.example.knowcourseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.knowcourseapp.fragments.CoursePrerequisitesFragment;
import com.example.knowcourseapp.models.Course;
import com.example.knowcourseapp.network.JsonUtility;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView courseTitleView;
    TextView courseDescriptionView;
    Button writeReviewButton;
    Course course;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_datails);
        courseTitleView = findViewById(R.id.courseTitle);
        courseDescriptionView = findViewById(R.id.courseDescription);
        writeReviewButton = findViewById(R.id.writeReview);


        Bundle extras = getIntent().getExtras();
        course = Course.getCourse(extras.getString("courseCode"));
        if (course != null) {

            //TODO: move to separate activity
            String json = "{\n" +
                    "    \"course\": \"COMP3330\",\n" +
                    "        \"date_created\": \"2020-11-01\",\n" +
                    "        \"rating\": 2,\n" +
                    "        \"year_taken\": 2019,\n" +
                    "        \"subclass\": \"B\",\n" +
                    "        \"professor\": \"Some Professor\",\n" +
                    "        \"assessment\": \"exam, project\",\n" +
                    "        \"grade\": 5,\n" +
                    "        \"review\": \"Good course. Like it\",\n" +
                    "        \"suggestions\": \"sample suggestion\"\n" +
                    "}";
            writeReviewButton.setOnClickListener((v) -> {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> JsonUtility.postJson("http://10.0.2.2:8000/api/courses/COMP3330/reviews/", json));
            });
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), CourseReviewListActivity.class);
        intent.putExtra("courseCode", course.getCode());
        startActivity(intent);


    }
}
