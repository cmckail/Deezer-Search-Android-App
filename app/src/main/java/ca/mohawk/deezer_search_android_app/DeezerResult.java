package ca.mohawk.deezer_search_android_app;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single search result from the
 * generic deezer api search
 */
public class DeezerResult {
    //id of the song
    private String id;
    //the title of the song
    @SerializedName("title_short")
    private String title;
    //the duration of the track
    private String duration;
    //a link to preview the track on deezer.com
    private String preview;
    //the album art associated with the result
    @SerializedName("picture")
    private String image;
    //the artist of the track
    private DeezerArtist artist;
    //the album the track belongs to
    private DeezerAlbum album;

    public DeezerAlbum getAlbum() {
        return album;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getPreview() {
        return preview;
    }

    public String getImage() {
        return image;
    }

    public DeezerArtist getArtist() {
        return artist;
    }
}
