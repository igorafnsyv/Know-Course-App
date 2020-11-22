package hkucs.knowcourse.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import hkucs.knowcourse.R;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A product entry in the list of products.
 */
public class CourseEntry {
    private static final String TAG = CourseEntry.class.getSimpleName();

    public final Uri dynamicUrl;
    public final String url;
    public final String code;
    public final String title;
    public final String description;

    public CourseEntry(
            String code, String title, String dynamicUrl, String url, String price, String description) {
        this.code = code;
        this.title = title;
        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.description = description;
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of CourseEntry objects
     */
    public static List<CourseEntry> initProductEntryList(Resources resources) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> res = executorService.submit(() -> NetworkUtility.readJson("http://10.0.2.2:8000/api/courses"));
        try {
            Gson gson = new Gson();
            String response = res.get();
            System.out.println(response);
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int pointer;
                while ((pointer = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, pointer);
                }
            } catch (IOException exception) {
                Log.e(TAG, "Error writing/reading from the JSON file.", exception);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    Log.e(TAG, "Error closing the input stream.", exception);
                }
            }
            String jsonProductsString = writer.toString();
            gson = new Gson();
            Type productListType = new TypeToken<ArrayList<CourseEntry>>() {
            }.getType();
            return gson.fromJson(jsonProductsString, productListType);
        } catch (InterruptedException | ExecutionException e) {
            e.getCause().printStackTrace();
        }
        executorService.shutdown();

        return null;
    }
}