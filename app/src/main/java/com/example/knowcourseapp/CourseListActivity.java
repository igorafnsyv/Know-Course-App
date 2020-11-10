package com.example.knowcourseapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.adapters.CoursesAdapter;
import com.example.knowcourseapp.models.Course;

import java.util.*;

public class CourseListActivity extends AppCompatActivity {
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.courses);
        courses = Course.getCourses();
        CoursesAdapter adapter = new CoursesAdapter(courses);
        System.out.println(adapter == null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
