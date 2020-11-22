package hkucs.knowcourse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import hkucs.knowcourse.R;
import hkucs.knowcourse.activities.CreateReviewActivity;

public class CourseDetailFragment extends Fragment {
    private String code;
    private String title;
    private String description;
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvDescription;
    private Button writeReviewButton;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.course_detail_fragment, container, false);
        code = getArguments().getString("code");
        title = getArguments().getString("title");
        description = getArguments().getString("description");
        // Set up the tool bar
        setUpToolbar(view,code);

        tvTitle = view.findViewById(R.id.title);
        tvDescription = view.findViewById(R.id.description);

        tvTitle.setText(title);
        tvDescription.setText(description);

        writeReviewButton = view.findViewById(R.id.writeReview);

        writeReviewButton.setOnClickListener((v) -> {
            Intent intent = new Intent(v.getContext(), CreateReviewActivity.class);
            intent.putExtra("courseCode", code);
            startActivity(intent);
        });

        return view;
    }

    private void setUpToolbar(View view, String code) {
        toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NavigationHost) getActivity()).navigateTo(new CourseGridFragment(), false); // Navigate to the next Fragment
                }
            });
            toolbar.setTitle(code);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(v.getContext(), CourseReviewListActivity.class);
//        intent.putExtra("courseCode", course.getCode());
//        startActivity(intent);
//    }
}
