package com.example.knowcourseapp.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.R;
import com.example.knowcourseapp.adapters.CoursesAdapter;
import com.example.knowcourseapp.models.Course;

import java.util.*;

public class CourseListActivity extends AppCompatActivity {
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), Context.MODE_PRIVATE);
        String token = preferences.getString(getString(R.string.token), null);
        if (token == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


        RecyclerView recyclerView = findViewById(R.id.courses);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        courses = Course.getCourses();
        CoursesAdapter adapter = new CoursesAdapter(courses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
