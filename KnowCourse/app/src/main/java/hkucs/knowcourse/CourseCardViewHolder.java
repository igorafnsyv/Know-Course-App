package hkucs.knowcourse;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseCardViewHolder extends RecyclerView.ViewHolder  {

    public TextView courseCode;
    public TextView courseTitle;

    public CourseCardViewHolder(@NonNull View itemView) {
        super(itemView);
        courseCode = itemView.findViewById(R.id.course_code);
        courseTitle = itemView.findViewById(R.id.course_title);
    }
}
