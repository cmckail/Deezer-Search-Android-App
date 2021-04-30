package ca.mohawk.deezer_search_android_app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Responsible for handling the downloading and
 * displaying the results from a deezer api search query
 */
public class DeezerSongDownloadAsyncTask extends DeezerBaseDownloadAsyncTask{

    private String TAG = "==DeezerSongDownloadAsyncTask==";
    //track object to store the downloaded content
    public DeezerTrack track;
    //constants to label button
    private static String ADD_LABEL = "Add To Listen later";
    private static String REMOVE_LABEL = "Remove From Listen later";

    /**
     * default constructor
     * @param view the view where the response
     *             will be populated
     * @param activity The current activity @view
     *                 belongs to
     */
    public DeezerSongDownloadAsyncTask(@Nullable View view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void onPostExecute(String result){
        Gson gson = new GsonBuilder().create();
        this.track = gson.fromJson(result, DeezerTrack.class);
        if (this.track == null){
            Log.d("searchResultAsync", "error");
            NoResultsHandler(null, "Error with Connection to Deezer");
            return;
        }
//        else if(responseList.results.isEmpty()){
//            Log.d("searchResultAsync", "No Search Results Found");
//            NoResultsHandler(null, "No Search Results Found");
//            return;
//        }

        Activity v = SongDetailActivity.getCurrentActivity();
        ImageView songImage = v.findViewById(R.id.songImageView);
        new DownloadImageTask(songImage).execute(this.track.getAlbum().getPicture());
        TextView artistView = v.findViewById(R.id.songArtistView);
        TextView nameView = v.findViewById(R.id.songNameView);
        TextView albumView = v.findViewById(R.id.songAlbumView);
        TextView durView = v.findViewById(R.id.songDuratonView);
        TextView releaseView = v.findViewById(R.id.songReleaseDateView);
        TextView explicView = v.findViewById(R.id.songExplicitView);
        TextView bpmView = v.findViewById(R.id.songBpmView);
        TextView rankView = v.findViewById(R.id.songDeezerRank);
        TextView linkView = v.findViewById(R.id.songLinkView);
        Button lisLatBtn = v.findViewById(R.id.listenLaterBtn);
        int durMinutes = Integer.parseInt(track.getDuration())/60;
        int durSeconds = Integer.parseInt(track.getDuration())%60;
        durView.setText("Duration: "+String.format("%02d:%02d", durMinutes, durSeconds));
        artistView.setText("By " + track.getArtist().getName());
        nameView.setText(track.getTitle());
        albumView.setText("Album: " + track.getAlbum().getTitle());
        releaseView.setText("Release Date: " + track.getRelease_date());
        explicView.setText(track.getExplicit() ? "Explicit Content" : "");
        bpmView.setText("Beats Per Minute: " +track.getBpm());
        rankView.setText("Deezer Rank: #" + track.getRank() );
        linkView.setText(Html.fromHtml("<html><b>Link: </b><a href=" + '"'+track.getLink()+'"' + ">" + track.getLink()+"</a></html>"));
        linkView.setMovementMethod(LinkMovementMethod.getInstance());
        DeezerSongSQLiteHelper dbHelper = new DeezerSongSQLiteHelper(super.getCurrentActivity());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (dbHelper.IdExists(db, this.track.getId())){
            lisLatBtn.setOnClickListener(this::removeFromListenLater);
            lisLatBtn.setText(this.REMOVE_LABEL);
        }else{
            lisLatBtn.setOnClickListener(this::addToListenLater);
            lisLatBtn.setText(this.ADD_LABEL);
        }

    }

    protected void addToListenLater(View v){
        DeezerSongSQLiteHelper dbHelper = new DeezerSongSQLiteHelper(super.getCurrentActivity());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(dbHelper.ID, this.track.getId());
        values.put(dbHelper.TITLE, this.track.getTitle() );
        values.put(dbHelper.DURATION, this.track.getDuration());
        values.put(dbHelper.IMAGE_URL, this.track.getAlbum().getPicture());
        values.put(dbHelper.DURATION, this.track.getDuration());
        values.put(dbHelper.ARTIST_NAME, this.track.getArtist().getName());
        long newrowID = db.insert(dbHelper.MYTABLE, null, values);
        Log.d(TAG, "Successful add of row " + newrowID);
        db.close();
        swapBtnListener(v.findViewById(R.id.listenLaterBtn));
    }

    protected void removeFromListenLater(View v){
        DeezerSongSQLiteHelper dbHelper = new DeezerSongSQLiteHelper(super.getCurrentActivity());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long remove = dbHelper.delete(db, this.track.getId());
        Log.d(TAG, Long.toString(remove));
        swapBtnListener(v.findViewById(R.id.listenLaterBtn));

    }

    protected void swapBtnListener(Button btn){
        if (btn.getText().equals(this.ADD_LABEL)){
            btn.setOnClickListener(this::removeFromListenLater);
            btn.setText(this.REMOVE_LABEL);
        }else {
            btn.setOnClickListener(this::addToListenLater);
            btn.setText(this.ADD_LABEL);
        }
    }

    public void NoResultsHandler(View v, String msg){
        LinearLayout frame = super.getCurrentActivity().findViewById(R.id.SongDetailsContainer);
        frame.removeAllViews();
        TextView errorTextView = new TextView(super.getCurrentActivity());
        errorTextView.setText(msg);
        errorTextView.setTextColor(Color.WHITE);
        errorTextView.setGravity(Gravity.CENTER);

        errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        frame.addView(errorTextView);

    }
}
