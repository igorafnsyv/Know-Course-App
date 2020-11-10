package com.example.knowcourseapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowcourseapp.R;
import com.example.knowcourseapp.models.Course;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView courseName;

        public ViewHolder(View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            courseName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), this.courseName.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<Course> courses;

    public CoursesAdapter(List<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View courseView = inflater.inflate(R.layout.item_course, parent, false);
        ViewHolder viewHolder = new ViewHolder(courseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.ViewHolder holder, int position) {
        Course course = courses.get(position);
        TextView courseNameView = holder.courseName;
        String courseName = course.getCode() + " " + course.getTitle();
        courseNameView.setText(courseName);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
