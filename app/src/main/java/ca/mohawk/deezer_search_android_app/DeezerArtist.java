package ca.mohawk.deezer_search_android_app;

/**
 * Represents an Musical Artist returned
 * from the Deezer API
 */
public class DeezerArtist {
    //the Deezer id of the artist
    private String id;
    //the name of the the artist
    private String name;

    /**
     * default constructor
     */
    public DeezerArtist(){

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
