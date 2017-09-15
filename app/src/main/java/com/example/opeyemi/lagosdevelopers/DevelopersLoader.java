package com.example.opeyemi.lagosdevelopers;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by opeyemi on 9/15/2017.
 */
public class DevelopersLoader extends AsyncTaskLoader<ArrayList<Developer>> {

    //the api URL to load the developers in Lagos from
    private static final String urlString = "https://api.github.com/search/users?q=location:lagos";

    public DevelopersLoader(Context context){
        super(context);
    }

    @Override
    public ArrayList<Developer> loadInBackground() {
        return Utility.extractDevelopers(urlString);
    }
}
