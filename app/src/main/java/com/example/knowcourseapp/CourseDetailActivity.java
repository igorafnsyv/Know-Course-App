package com.example.knowcourseapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView courseTitleView;
    TextView courseDescriptionView;
    Course course;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_datails);
        courseTitleView = findViewById(R.id.courseTitle);
        courseDescriptionView = findViewById(R.id.courseDescription);

        Bundle extras = getIntent().getExtras();
        course = Course.getCourse(extras.getString("courseCode"));
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), CourseReviewListActivity.class);
        intent.putExtra("courseCode", course.getCode());
        startActivity(intent);


    }
}
