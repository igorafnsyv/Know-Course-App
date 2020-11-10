package com.example.knowcourseapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CourseListActivity extends ListActivity implements View.OnClickListener {

    HttpURLConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, String>> list;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> res = executorService.submit(() -> getJSON("http://10.0.2.2:8000/api/courses/"));
        try {
            Gson gson = new Gson();
            res.get();
            list = gson.fromJson(res.get(), List.class);
            List<Map<String, String>> courseList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("courseTitle", list.get(i).get("code") + " " + list.get(i).get("title"));
                map.put("courseDescription", list.get(i).get("description"));
                courseList.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, courseList, R.layout.activity_course_list,
                    new String[] {"courseTitle", "courseDescription"}, new int[] {R.id.courseTitle, R.id.courseDescription});
            setListAdapter(adapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();


    }

    //TODO: checking add response code verification and its handling
    public String getJSON(String url) {
        String response = "";
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setInstanceFollowRedirects(true);
            System.out.println(connection.getResponseCode());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder htmlSourceBuilder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                htmlSourceBuilder.append(line);
                htmlSourceBuilder.append(System.lineSeparator());
            }
            response = htmlSourceBuilder.toString();
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            return "failed to connect";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }


    @Override
    public void onClick(View view)
    {
        System.out.println("clicked");
        TextView courseTitleView = (TextView) ((ViewGroup) view).getChildAt(0);
        Toast.makeText(this, courseTitleView.getText().toString(), Toast.LENGTH_LONG).show();
    }


}