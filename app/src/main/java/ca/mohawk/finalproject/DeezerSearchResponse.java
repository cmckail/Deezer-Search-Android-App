package ca.mohawk.finalproject;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Represents the data jsonArray returned by
 * the Deezer API search request
 */
public class DeezerSearchResponse{
    @SerializedName("data")
    ArrayList<DeezerResult> results = new ArrayList<DeezerResult>();

    /**
     * default constructor
     */

    public DeezerSearchResponse() {
    }

}
