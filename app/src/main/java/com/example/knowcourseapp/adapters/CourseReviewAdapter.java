package com.example.knowcourseapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.R;
import com.example.knowcourseapp.models.CourseReview;

import java.util.List;
import java.util.Objects;

public class CourseReviewAdapter extends RecyclerView.Adapter<CourseReviewAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView dateCreated;
        public TextView rating;
        public TextView yearTaken;
        public TextView subclass;
        public TextView professor;
        public TextView assessment;
        public TextView grade;
        public TextView workload;
        public TextView review;
        public TextView suggestions;

        public ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            dateCreated = itemView.findViewById(R.id.date_created);
            rating = itemView.findViewById(R.id.rating);
            yearTaken = itemView.findViewById(R.id.yearTaken);
            subclass = itemView.findViewById(R.id.subclass);
            professor = itemView.findViewById(R.id.professor);
            assessment = itemView.findViewById(R.id.assessment);
            grade= itemView.findViewById(R.id.grade);
            workload = itemView.findViewById(R.id.workload);
            review = itemView.findViewById(R.id.review);
            suggestions = itemView.findViewById(R.id.suggestions);

        }

    }

    private List<CourseReview> reviews;

    public CourseReviewAdapter(List<CourseReview> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public CourseReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView = inflater.inflate(R.layout.item_review, parent, false);
        ViewHolder viewHolder = new ViewHolder(reviewView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseReviewAdapter.ViewHolder holder, int position) {
        CourseReview courseReview = reviews.get(position);
        TextView authorView = holder.author;
        TextView dateCreatedView = holder.dateCreated;
        TextView ratingView = holder.rating;
        TextView yearTakenView = holder.yearTaken;
        TextView subclassView = holder.subclass;
        TextView professorView = holder.professor;
        TextView assessmentView = holder.assessment;
        TextView gradeView = holder.grade;
        TextView workloadView = holder.workload;
        TextView reviewView = holder.review;
        TextView suggestionsView = holder.suggestions;

        Resources resources = holder.itemView.getResources();
        String author = Objects.toString(courseReview.getAuthor(), "");
        String dateCreated = Objects.toString(courseReview.getDateCreated(), "");
        String rating = Objects.toString(courseReview.getRating(), "");
        String yearTaken = Objects.toString(courseReview.getYearTaken(), "");
        String subclass = Objects.toString(courseReview.getSubclass(), "");
        String professor = Objects.toString(courseReview.getProfessor(), "");
        String assessment = Objects.toString(courseReview.getAssessment(), "");
        String grade = Objects.toString(CourseReview.intToGrade(courseReview.getGrade()), "");
        String workload = Objects.toString(CourseReview.intToWorkload(courseReview.getWorkload()), "");
        String review = Objects.toString(courseReview.getReview(), "");
        String suggestions = Objects.toString(courseReview.getSuggestions(), "");

        authorView.setText(resources.getString(R.string.author, author));
        dateCreatedView.setText(resources.getString(R.string.date_created, dateCreated));
        ratingView.setText(resources.getString(R.string.rating, rating));
        yearTakenView.setText(resources.getString(R.string.year_taken,yearTaken));
        subclassView.setText(resources.getString(R.string.subclass, subclass));
        professorView.setText(resources.getString(R.string.professor, professor));
        assessmentView.setText(resources.getString(R.string.assessment, assessment));
        gradeView.setText(resources.getString(R.string.grade, grade));
        workloadView.setText(resources.getString(R.string.workload, workload));
        reviewView.setText(resources.getString(R.string.review, review));
        suggestionsView.setText(resources.getString(R.string.suggestion, suggestions));


    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
