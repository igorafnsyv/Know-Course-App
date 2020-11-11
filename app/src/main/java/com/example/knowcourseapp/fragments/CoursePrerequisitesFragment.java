package com.example.knowcourseapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.knowcourseapp.CourseDetailActivity;
import com.example.knowcourseapp.R;

public class CoursePrerequisitesFragment extends ListFragment {

    public CoursePrerequisitesFragment() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String[] prerequisites = getArguments().getStringArrayList("prerequisites").toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.prerequisite_list_item, prerequisites);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), CourseDetailActivity.class);
        intent.putExtra("courseCode", ((TextView) v).getText());
        v.getContext().startActivity(intent);
    }

}
