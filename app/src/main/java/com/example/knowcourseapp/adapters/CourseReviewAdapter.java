package com.example.knowcourseapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.CourseDetailActivity;
import com.example.knowcourseapp.R;
import com.example.knowcourseapp.models.CourseReview;

import java.util.List;

public class CourseReviewAdapter extends RecyclerView.Adapter<CourseReviewAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView dateCreated;
        public TextView rating;
        public TextView yearTaken;
        public TextView subclass;

        public ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            dateCreated = itemView.findViewById(R.id.date_created);
            rating = itemView.findViewById(R.id.rating);
            yearTaken = itemView.findViewById(R.id.yearTaken);
            subclass = itemView.findViewById(R.id.subclass);

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
        TextView rating = holder.rating;
        TextView yearTaken = holder.yearTaken;
        TextView subclass = holder.subclass;

        authorView.setText(courseReview.getAuthor());
        dateCreatedView.setText(courseReview.getDateCreated());
        rating.setText(courseReview.getRating());
        yearTaken.setText(courseReview.getYearTaken());
        subclass.setText(courseReview.getSubclass());


    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
