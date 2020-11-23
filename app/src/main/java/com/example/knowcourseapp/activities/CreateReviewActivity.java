package com.example.knowcourseapp.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowcourseapp.R;
import com.example.knowcourseapp.models.CourseReview;
import com.example.knowcourseapp.network.NetworkUtility;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateReviewActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    EditText ratingView;
    EditText yearTakenView;
    Spinner gradeSpinner;
    EditText subclassView;
    EditText professorView;
    EditText assessmentView;
    EditText workloadView;
    EditText reviewView;
    EditText suggestionsView;
    String COURSE_CODE;
    String SELECTED_GRADE;

    Resources resources;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        COURSE_CODE = getIntent().getStringExtra("courseCode");

        ratingView = findViewById(R.id.ratingInput);
        yearTakenView = findViewById(R.id.yearTakenInput);
//        gradeView = findViewById(R.id.gradeInput);
        subclassView = findViewById(R.id.subclassInput);
        professorView = findViewById(R.id.professorInput);
        assessmentView = findViewById(R.id.assessmentInput);
        workloadView = findViewById(R.id.workloadInput);
        reviewView = findViewById(R.id.reviewInput);
        suggestionsView = findViewById(R.id.suggestionsInput);

        gradeSpinner = findViewById(R.id.gradeSpinner);
        gradeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,  R.array.grades_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);




        resources = getResources();

    }

    @Override
    public void onClick(View v) {
        boolean errorPresent = false;

        if (TextUtils.isEmpty(ratingView.getText())) {
            ratingView.setError("Cannot be blank");
            errorPresent = true;
        }

        if (TextUtils.isEmpty(yearTakenView.getText())) {
            yearTakenView.setError("Cannot be blank");
            errorPresent = true;
        }

        if (TextUtils.isEmpty(professorView.getText())) {
            professorView.setError("Cannot be blank");
            errorPresent = true;
        }

        if (TextUtils.isEmpty(workloadView.getText())) {
            workloadView.setError("Cannot be blank");
            errorPresent = true;
        }

        if (TextUtils.isEmpty(reviewView.getText())) {
            reviewView.setError("Cannot be blank");
            errorPresent = true;
        }

        if (!errorPresent) {
            String author = "";
            int rating = Integer.parseInt(ratingView.getText().toString());
            String yearTaken = yearTakenView.getText().toString();
            String subclass = subclassView.getText().toString();
            String professor = professorView.getText().toString();
            String assessment = assessmentView.getText().toString();
            int grade = CourseReview.gradeToInt(SELECTED_GRADE);

            int workload = Integer.parseInt(workloadView.getText().toString());
            String reviewText = reviewView.getText().toString();
            String suggestions = suggestionsView.getText().toString();

            CourseReview review = new CourseReview(author, "", rating, yearTaken,
                    subclass, professor, assessment, grade, workload, reviewText, suggestions);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            String json = gson.toJson(review, CourseReview.class);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            String url = resources.getString(R.string.server_address) +  resources.getString(R.string.course_review_endpoint, COURSE_CODE);

            //TODO: handle verification of code
            executor.submit(() -> NetworkUtility.postJson(url, json, this));
            finish();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SELECTED_GRADE = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("nothing");
    }
}