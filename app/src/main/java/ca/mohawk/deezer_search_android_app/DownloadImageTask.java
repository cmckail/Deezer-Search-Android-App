package ca.mohawk.deezer_search_android_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Async Task responsible for downloading an image
 * via http, assembling the bitmap and assigning
 * to the passed ImageView
 */
public class DownloadImageTask
        extends AsyncTask<String, Void, Bitmap> {
    //tag for logging
    private static String TAG = "==DownloadImageTask==";
    //Constant for HTTP OK
    private static int HTTP_OK = 200;
    //Represents the ImageView being used
    ImageView bmImage;

    /**
     * Constructor for DownloadTask
     * @param bmImage The ImageView for the bitmap
     *                to be assigned to
     */
    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    /**
     * Responsible for establishing http connection
     * and returning the assembled bitmap.
     * If not successfully assembled null is returned
     * @param urls
     * @return
     */
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmp = null;
        int statusCode = -1;
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            statusCode = conn.getResponseCode();
            if (statusCode == HTTP_OK) {
                bmp = BitmapFactory.decodeStream(conn.getInputStream());
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "bad URL " + e + " url: " + urls[0]);
        } catch (IOException e) {
            Log.d(TAG, "bad I/O " + e);
        }
        Log.d(TAG, "done " + statusCode);
        return bmp;
    }

    /**
     * Assigns the bitmap bmImage if not null
     * @param result the Bitmap from doinbackground
     */
    protected void onPostExecute(Bitmap result) {
        if (result != null)
            bmImage.setImageBitmap(result);
    }
}
