package ca.mohawk.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Helper class to access the Musica DB
 */
public class DeezerSongSQLiteHelper extends SQLiteOpenHelper {

    public static final String TAG = "==DeezerSongSQLiteHelper==";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_FILE_NAME = "Musica.db";
    public static final String MYTABLE = "DeezerTracks";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DURATION = "duration";
    public static final String ARTIST_NAME = "artist_name";
    public static final String IMAGE_URL = "image_url";

    /**
     * Create statement for musica.db
     */
    private static final String SQL_CREATE =
            "CREATE TABLE " + MYTABLE + "( " + ID + " INTEGER PRIMARY KEY, " +
                    TITLE + " TEXT, " + DURATION + " TEXT, " + ARTIST_NAME + " TEXT, "
                    + IMAGE_URL + " TEXT " +" )";

    /**
     * default Constructor
     * @param context
     */
    public DeezerSongSQLiteHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);

        Log.d(TAG, "constructor");
    }

    @Override
    /**
     * Create the database
     */
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate " + SQL_CREATE);
        db.execSQL(SQL_CREATE);
    }

    /**
     * never used, but added to meet the abstract
     * class requirements
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * handles the deletion of a row based on id
     * @param db
     * @param ID
     * @return
     */
    public long delete(SQLiteDatabase db, String ID){
        ContentValues values = new ContentValues();
        return db.delete(this.MYTABLE, this.ID+"=?", new String[]{ID});
    }

    /**
     * Checks if an id exists within the database
     * @param db
     * @param mID
     * @return
     */
    public boolean IdExists(SQLiteDatabase db, String mID){

        String query = "SELECT " + this.ID +
                " FROM " + this.MYTABLE + " WHERE " + this.ID + " = ?";
        Cursor c = db.rawQuery(query, new String[]{mID});

        return c.getCount() > 0;

    }


}
