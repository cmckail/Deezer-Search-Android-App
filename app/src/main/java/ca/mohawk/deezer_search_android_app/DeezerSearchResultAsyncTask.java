package ca.mohawk.deezer_search_android_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Responsible for handling the downloading and
 * displaying the results from a deezer api search query
 */
public class DeezerSearchResultAsyncTask extends DeezerBaseDownloadAsyncTask{
    //list of responses
    private String TAG = "==DeezerSearchResultAsyncTask==";
    private List<DeezerResult> responseList;

    /**
     * default constructor
     * @param view the view where the response will
     *             be populated
     * @param activity The current activity @view
     *                 belongs to
     */
    public DeezerSearchResultAsyncTask(@Nullable View view, Activity activity) {
        super(view, activity);
    }

    /**
     * handles the processing of the data returned
     * from @doInBackground
     * @param result
     */
    @Override
    protected void onPostExecute(String result){
        //if result is null there was an error with connection
        if (result == null){
            NoResultsHandler(null, "Error with Connection to Deezer");
            Log.d(TAG, "no results");
            return;
        }

        DeezerSearchResponse responseList = null;
        //cast the listView onto the passed in View
        ListView listView = (ListView)super.getPassedView();

        //initialize gson with gsonBuilder
        Gson gson = new GsonBuilder().create();
        //attempt to create DeezerSearchResponse Object
        responseList = gson.fromJson(result, DeezerSearchResponse.class);
        //if response is null, then connection failed
        if (responseList == null){
            Log.d("searchResultAsync", "error");
            NoResultsHandler(null, "Error with Connection to Deezer");
            return;
        }
        //if list empty, then no search results matching query
        else if(responseList.results.isEmpty()){
            Log.d("searchResultAsync", "No Search Results Found");
            NoResultsHandler(null, "No Search Results Found");
            return;
        }
        //initialize arrayAdapter
        DeezerResultArrayAdapter adapter = new DeezerResultArrayAdapter(super.getCurrentActivity(),R.layout.adapter_view_layout, responseList.results );
        //store results in responseList to be used by ItemClick Handler
        this.responseList = responseList.results;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this::onItemClick);
    }

    /**
     * on item click launches the SongDetail Activity
     * and send the required song id through intent
     * @param parent adapterView
     * @param v
     * @param position
     * @param id
     */
    public void onItemClick(AdapterView parent, View v, int position, long id){
        Intent intent = new Intent(super.getCurrentActivity(), SongDetailActivity.class);
        intent.putExtra("id", this.responseList.get(position).getId());
        super.getCurrentActivity().startActivity(intent);
    }

    /**
     * handles clearing the current view and placing an error
     * message via a new TextView
     * @param v
     * @param msg
     */
    public void NoResultsHandler(View v, String msg){
        //Get the Frame containing the listView and remove children
        FrameLayout frame = super.getCurrentActivity()
                .findViewById(R.id.listFrame);
        frame.removeAllViews();
        //Create error message and add to View
        TextView errorTextView =
                new TextView(super.getCurrentActivity());
        errorTextView.setText(msg);
        errorTextView.setTextColor(Color.WHITE);
        errorTextView.setGravity(Gravity.CENTER);
        errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        frame.addView(errorTextView);

    }
}
