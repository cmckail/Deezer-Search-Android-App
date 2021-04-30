package ca.mohawk.finalproject;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a track returned by the deezer api
 */
public class DeezerTrack {
    //unique id of track
    private String id;
    //title of the track
    @SerializedName("title_short")
    private String title;
    //link to the song on deezer
    private String link;
    //the duration
    private String duration;
    //the deezer rank
    private String rank;
    //the release date
    private String release_date;
    //beats per minute of the song
    private String bpm;
    //artist of the track
    private DeezerArtist artist;
    //artist of the album
    private DeezerAlbum album;
    //if song is explicit
    @SerializedName("explicit_lyrics")
    private Boolean explicit;


    public String getId() {
        return id;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDuration() {
        return duration;
    }

    public String getRank() {
        return rank;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getBpm() {
        return bpm;
    }

    public DeezerArtist getArtist() {
        return artist;
    }

    public DeezerAlbum getAlbum() {
        return album;
    }
}
