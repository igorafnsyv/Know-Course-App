package com.example.knowcourseapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.adapters.CourseReviewAdapter;
import com.example.knowcourseapp.models.CourseReview;

import java.util.List;

public class CourseReviewListActivity extends Activity {

    List<CourseReview> courseReviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reviews);
        RecyclerView recyclerView = findViewById(R.id.courseReviews);
        // Get course code from intent
        Bundle extras = getIntent().getExtras();
        courseReviews = CourseReview.getCourseReview(extras.getString("courseCode"));
        CourseReviewAdapter adapter = new CourseReviewAdapter(courseReviews);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
