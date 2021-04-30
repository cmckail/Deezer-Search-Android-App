package ca.mohawk.deezer_search_android_app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * cursor adapter used to populate listview
 * with deezer results saved to the db
 */
public class DeezerResultCursorAdapter extends SimpleCursorAdapter {

    //the id of the layout being used
    private int layout;
    //the array list containing ids
    private ArrayList<Integer> idList = new ArrayList<Integer>();

    public DeezerResultCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context,layout,c,from,to);
        this.layout=layout;
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    /**
     * create the NewView
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layout, null);
    }

    /**
     * bind values to the newly created
     * list view from @method newView
     * @param view the current view
     * @param context context view belongs to
     * @param cursor the cursor from db query
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        //get text and image views from the current view instnace
        TextView title = view.findViewById(R.id.trackView);
        TextView artist = view.findViewById(R.id.ArtistView);
        TextView durationView = view.findViewById(R.id.DurView);
        ImageView image = view.findViewById(R.id.TrackImageView);

        //get the index number of the columns from the cursor
        int idIndex = cursor.getColumnIndex(DeezerSongSQLiteHelper.ID);
        int titleIndex = cursor.getColumnIndex(DeezerSongSQLiteHelper.TITLE);
        int artistIndex = cursor.getColumnIndex(DeezerSongSQLiteHelper.ARTIST_NAME);
        int durIndex = cursor.getColumnIndex(DeezerSongSQLiteHelper.DURATION);
        int imageIndex = cursor.getColumnIndex(DeezerSongSQLiteHelper.IMAGE_URL);

        //get and format duration
        int duration = Integer.parseInt(cursor.getString(durIndex));
        int durMinutes = duration/60;
        int durSeconds = duration%60;

        //set textviews from current row of cursor
        title.setText(cursor.getString(titleIndex));
        artist.setText(cursor.getString(artistIndex));
        durationView.setText( String.format("%02d:%02d", durMinutes, durSeconds));
        //store the current id into idList
        idList.add(cursor.getInt(idIndex));
        //attempt to download and display the image from the image url
        new DownloadImageTask(image).execute(cursor.getString(imageIndex));





    }
}
