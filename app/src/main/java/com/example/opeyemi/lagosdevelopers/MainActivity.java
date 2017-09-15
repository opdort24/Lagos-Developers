package com.example.opeyemi.lagosdevelopers;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Developer>> {


    private ProgressBar progressBar;  //progress indicator while loading data in background
    private TextView emptyView;       //empty view to be displayed when there is problem loading data
    private DevelopersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting an object of the list view
        ListView developersListView = (ListView) findViewById(R.id.list);

        //set a listener callback to be called when an item on the list is clicked
        developersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //set the developer information to be displayed to the current developer clicked
                //create an explicit intent to developerProfileActivity
                DeveloperProfileActivity.setDeveloper(adapter.getItem(position));
                Intent intent = new Intent(MainActivity.this, DeveloperProfileActivity.class);
                startActivity(intent);
            }
        });


        //check for suitable  connection before continuing to load the data into the field
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()){
            getLoaderManager().initLoader(1,null,this).forceLoad();
        } else {
            //Hide the progress bar
            progressBar = (ProgressBar) findViewById(R.id.main_progressBar);
            progressBar.setVisibility(View.GONE);


            //Create an empty state textView to display if there is no internet connection
            emptyView = (TextView) findViewById(R.id.no_earthquake_textView);
            emptyView.setText("No internet connection");
        }


    }

    @Override
    public Loader<ArrayList<Developer>> onCreateLoader(int id, Bundle args) {
        return new DevelopersLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Developer>> loader, ArrayList<Developer> data) {

        //Hide the progress bar and display the developers result found or no result notification
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);
        progressBar.setVisibility(View.GONE);



        ListView developersListView = (ListView) findViewById(R.id.list);
        //Create an empty state textView to display if the adapter is empty
        emptyView = (TextView) findViewById(R.id.no_earthquake_textView);
        emptyView.setText("No Developer found");
        developersListView.setEmptyView(emptyView);


        // Create a new {@link ArrayAdapter} for developers
        adapter = new DevelopersAdapter(MainActivity.this,data);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        developersListView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Developer>> loader) {
        adapter.clear();
    }
}
