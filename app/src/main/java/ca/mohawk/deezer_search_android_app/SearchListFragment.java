package ca.mohawk.deezer_search_android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that displays search results from a
 * user query. If no results are found, fragment
 * displays an error message
 *
 *
 */
public class SearchListFragment extends Fragment {
    private static final String TAG = "==SearchListFragment==";
    //List of possible sort parameters for URI
    private String[] sorts = new String[]
            {"RANKING", "TRACK_ASC", "TRACK_DESC",
            "ARTIST_ASC", "ARTIST_DESC",
            "RATING_ASC", "RATING_DESC"};
    private String artist;
    private String track;
    private int sort;

    /**
     * Constructor for the searchListFragment.
     * No default constructor as this fragment
     * requires inputs.
     * @param artist String value of artist
     * @param track String value of track name
     * @param sort position of sort method chosen
     */
    public SearchListFragment(String artist, String track, int sort) {
        this.artist = artist;
        this.track = track;
        this.sort = sort;
    }

    public SearchListFragment(){

    }

    /**
     * handles the Create LifeCycle stage
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * handles the creation of the view on Create
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for fragment
        return inflater.inflate
                (R.layout.fragment_list_layout,
                        container, false);
    }

    /**
     * Downloads the results when fragment is resumed
     */
    @Override
    public void onResume() {
        super.onResume();
        PopulateSearchResults(getView());
    }

    /**
     * formats the deezer api call,
     * and calls async task to handle the download
     * and return of results
     * @param view current View being used
     */
    public void PopulateSearchResults( @NonNull View view) {
        DeezerSearchResultAsyncTask dl = new DeezerSearchResultAsyncTask(
                view.findViewById(R.id.list_view), getActivity());

        String uri = "https://api.deezer.com/search?q=";
        if (!this.artist.equals(""))
            uri +="artist:" + '"'+ artist + '"';

        if (!this.track.equals(""))
            uri +="track:" +'"' +track+ '"';
        uri +="&order="+this.sorts[this.sort];
        Log.d(TAG, "PopulateSearchResults " + uri);
        dl.execute(uri.replaceAll(" ","%20"));
    }


}