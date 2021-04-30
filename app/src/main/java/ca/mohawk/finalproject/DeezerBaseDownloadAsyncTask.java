package ca.mohawk.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Base class used to handle api queries to the
 * Deezer API
 */
public class DeezerBaseDownloadAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "==download async==";
    private static View passedView;
    private Activity currentActivity;

    /**
     *
     * @param view the view where the results belong
     * @param activity the current active activity
     */
    public DeezerBaseDownloadAsyncTask(@Nullable View view, Activity activity) {
        this.passedView = view;
        this.currentActivity = activity;
    }

    public static View getPassedView() {
        return passedView;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Download data from the supplied URL,
     * and catch exceptions
     *
     * @param params - first parameter is the URL
     * @return a string that concatenates all of the output together, null on failure
     */
    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "Starting Background Task");
        StringBuilder results = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            String line = null;
            // Open the Connection - GET is the default setRequestMethod
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            //timeout connection after 1.5 seconds
            url.openConnection().setConnectTimeout(1500);


            // Read the response
            int statusCode = conn.getResponseCode();
            Log.d(TAG, "Status Code: " + statusCode);
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(
                        conn.getInputStream());
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream,
                                "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    results.append(line);
                }
            }
            Log.d(TAG, "Data received = " + results.length());
             Log.d(TAG, "Response Code: " + statusCode);
        } catch (IOException ex) {
            Log.d(TAG, "Caught Exception: " + ex);
        }
        return results.toString();
    }



}
