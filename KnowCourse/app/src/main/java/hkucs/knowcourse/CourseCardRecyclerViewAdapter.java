package hkucs.knowcourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hkucs.knowcourse.network.CourseEntry;
import hkucs.knowcourse.network.ImageRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class CourseCardRecyclerViewAdapter extends RecyclerView.Adapter<CourseCardViewHolder> {

    private List<CourseEntry> courseList;
    private List<CourseEntry> courseListCopy;
    private ImageRequester imageRequester;

    CourseCardRecyclerViewAdapter(List<CourseEntry> courseList) {
        this.courseList = courseList;
        courseListCopy = new ArrayList<>(courseList);
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public CourseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new CourseCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCardViewHolder holder, int position) {
        if (courseList != null && position < courseList.size()) {
            final CourseEntry course = courseList.get(position);
            holder.courseCode.setText(course.code);
            holder.courseTitle.setText(course.title);
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void filter(String text) {
        courseList.clear();
        if(text.isEmpty()){
            courseList.addAll(courseListCopy);
        } else{
            text = text.toLowerCase();
            for(CourseEntry course: courseListCopy){
                if(course.code.toLowerCase().contains(text) || course.code.toLowerCase().contains(text)){
                    courseList.add(course);
                }
            }
        }
        notifyDataSetChanged();
    }
}
