package ca.mohawk.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFormFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchFormFragment extends Fragment {



    public SearchFormFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_form, container, false);
        Spinner spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), R.layout.spinner_layout,
                getResources().getStringArray(R.array.sort_array));
        spinner.setAdapter(adapter);
        Button searchBtn = v.findViewById(R.id.SearchBtn);
        Button resetBtn = v.findViewById(R.id.resetBtn);
        searchBtn.setOnClickListener(this::CompleteSearch);
        resetBtn.setOnClickListener(this::ResetForm);
        return v;
    }

    /**
     * validates the inputs and launches the
     * searchListenFragment
     * @param v
     */
    private void CompleteSearch(@Nullable View v){
        v = getView();
        //get the input views
        TextView ArtistNameView = v.findViewById(R.id.ArtistNameEditView);
        TextView TrackNameView = v.findViewById(R.id.TrackNameEditView);
        Spinner sortSpinner = v.findViewById(R.id.spinner);

        //get the values to be passed to the fragment
        int spinnerPosition = sortSpinner.getSelectedItemPosition();

        //check if char after trimming spaces to eliminate invalid searches
        String Artist = ArtistNameView.getText().toString().trim().equals("")? ""
                :ArtistNameView.getText().toString();
        String TrackName = TrackNameView.getText().toString().trim().equals("")? ""
                :TrackNameView.getText().toString();
        //send error toast if no text inputs filled.
        if (Artist.equals("") && TrackName.equals("")){
            Toast.makeText(getContext(),
                    "Artist or Track must be filled out to complete search", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //Launch next Fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.MyContainer,
                new SearchListFragment(Artist, TrackName, spinnerPosition));
        fragmentTransaction.commit();

    }

    /**
     * resets the text inputs
     * @param v View . nullable
     */
    private void ResetForm(@Nullable View v){
        v = getView();
        TextView ArtistNameView = v.findViewById(R.id.ArtistNameEditView);
        TextView TrackNameView = v.findViewById(R.id.TrackNameEditView);
        ArtistNameView.setText("");
        TrackNameView.setText("");
    }
}