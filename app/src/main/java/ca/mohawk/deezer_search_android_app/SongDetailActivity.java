package ca.mohawk.deezer_search_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class SongDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String TAG = "== Main Activity 3 ==";
    private static SongDetailActivity currentActivity = null;
    private DrawerLayout myDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        currentActivity = this;
        // Access myDrawer
        myDrawer = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        //Access the ActionBar, enable "home" icon
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayHomeAsUpEnabled(true);
        // Add an ActionBarDrawerToggle element
        ActionBarDrawerToggle myactionbartoggle = new
                ActionBarDrawerToggle(this, myDrawer,
                (R.string.open), (R.string.close));
        myDrawer.addDrawerListener(myactionbartoggle);
        myactionbartoggle.syncState();
        // set up callback method for Navigation View
        NavigationView myNavView = (NavigationView)
                findViewById(R.id.nav_view);
        myNavView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadSongDetails(null);
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * handles the api call to get the track data
     * from the deezer api
     * @param view
     */
    public void DownloadSongDetails(View view) {
        DeezerSongDownloadAsyncTask dl = new DeezerSongDownloadAsyncTask(null, getCurrentActivity());
        // Build call to Webservice
        Intent intent = getIntent();
        String uri = "https://api.deezer.com/track/" + intent.getStringExtra("id");
        Log.d(TAG, "DownloadSongDetails " + uri);
        dl.execute(uri);
    }

    /**
     * handles navigating the user to a new activity based on navigation item selected
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        myDrawer.closeDrawers();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_one:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_two:
                intent = new Intent(this, ListenLaterActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    /**
     * handles home item on the menu bar
     * opens and closes the navigation drawer
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Find out the current state of the drawer (open or closed)
        boolean isOpen = myDrawer.isDrawerOpen(GravityCompat.START);
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Home button - open or close the drawer
                if (isOpen == true) {
                    myDrawer.closeDrawer(GravityCompat.START);
                } else {
                    myDrawer.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}