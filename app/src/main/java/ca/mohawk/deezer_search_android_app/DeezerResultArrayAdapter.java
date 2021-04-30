package ca.mohawk.deezer_search_android_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Array adapter used to populate the results of a
 * deezer search query into a listview
 */
public class DeezerResultArrayAdapter extends ArrayAdapter<DeezerResult> {
    //the current context
    private Context context;
    //the id of the layout being used
    private int resource;
    //a list of ids representing the content
//    public ArrayList<String> idList;
    public DeezerResultArrayAdapter(@NonNull Context context, int resource, @NonNull List<DeezerResult> objects) {
        super(context, resource, objects);
        //this.context = context;
        this.resource = resource;

    }

    /**
     * populates the textview and picture view
     * of the custom layout passed
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the object values needed for the view
        String trackName = getItem(position).getTitle();
        String artist = getItem(position).getArtist().getName();
        String imageURL = getItem(position).getAlbum().getPicture();
        int duration = Integer.parseInt(getItem(position).getDuration());
        int durMinutes = duration/60;
        int durSeconds = duration%60;

        //inflate the layout and get view
        LayoutInflater inflater = LayoutInflater.from(super.getContext());
        convertView = inflater.inflate(resource, parent, false);

        //get the text and image views of View
        TextView trackView = convertView.findViewById(R.id.trackView);
        TextView artistView = convertView.findViewById(R.id.ArtistView);
        TextView durView = convertView.findViewById(R.id.DurView);
        ImageView trackArt = convertView.findViewById(R.id.TrackImageView);
        //attempt to display image from url passed
        new DownloadImageTask(trackArt).execute(imageURL);
        //assign text values
        trackView.setText(trackName);
        artistView.setText(artist);
        durView.setText(String.format("%02d:%02d", durMinutes, durSeconds));


        return convertView;

    }
}
