package ca.mohawk.finalproject;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Musical Album from the Deezer API
 * Primarily used by GsonBuilder
 */
public class DeezerAlbum {

    //Album Deezer id
    private String id;
    //Official Album Title
    private String title;
    //Cover art of album in medium size
    @SerializedName("cover_medium")
    private String picture;

    /**
     * default constructor for DeezerAblum
     */
    public DeezerAlbum() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }
}
