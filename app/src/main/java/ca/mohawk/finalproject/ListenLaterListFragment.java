package ca.mohawk.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * displays all of the tracks saved by the user
 */
public class ListenLaterListFragment extends Fragment {
    private ArrayList<Integer> idPositionArray;

    public ListenLaterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_layout, container, false);
//        populateListView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView(getView());
    }

    /**
     *
     * launches the songDetailActivity and sends the
     * id of the selected listView item
     */
    public void onItemClick(AdapterView parent, View v, int position, long id){
        Intent intent = new Intent(getContext(), SongDetailActivity.class);
        intent.putExtra("id", Integer.toString(idPositionArray.get(position)));
        this.getActivity().startActivity(intent);
    }

    /**
     * handles the query and population of the listView
     * @param v
     */
    public void populateListView(View v){
        //get db and helper
        DeezerSongSQLiteHelper sqLiteHelper = new DeezerSongSQLiteHelper(getContext());
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        //select all from musica.db
        Cursor c = db.rawQuery("SELECT * FROM " + sqLiteHelper.MYTABLE, null);
        //if no rows exist
        if (c.getCount() < 1){
            //remove all children of frame
            FrameLayout frame = getActivity().findViewById(R.id.listFrame);
            frame.removeAllViews();
            //create error message
            TextView errorTextView = new TextView(getContext());
            errorTextView.setText("You haven't added any songs to listen later yet!");
            errorTextView.setTextColor(Color.WHITE);
            errorTextView.setGravity(Gravity.CENTER);
            errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            frame.addView(errorTextView);
            return;
        }
        //initialize cursorAdapter
        DeezerResultCursorAdapter adapter = new DeezerResultCursorAdapter(getContext(),
                R.layout.adapter_view_layout, c,
                new String[]{sqLiteHelper.TITLE, sqLiteHelper.ARTIST_NAME, sqLiteHelper.DURATION,
                        sqLiteHelper.IMAGE_URL},
                new int[]{R.id.trackView, R.id.ArtistView, R.id.DurView, R.id.TrackImageView}, 0);
        ListView listView = v.findViewById(R.id.list_view);
        //store the listofIds in fragment
        idPositionArray = adapter.getIdList();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this::onItemClick);
    }

}