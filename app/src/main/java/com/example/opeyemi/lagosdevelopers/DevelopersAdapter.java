package com.example.opeyemi.lagosdevelopers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by opeyemi on 9/13/2017.
 */
public class DevelopersAdapter extends ArrayAdapter<Developer> {



    public DevelopersAdapter(Context context,ArrayList developers){

        super(context,0,developers);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // initialise a view to be reused
        View listView = convertView;

        //inflate the view with a new list if no view has previously been used
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Developer developer = getItem(position);

        //display the image in the of the developer
        ImageView photoImageView = (ImageView) listView.findViewById(R.id.list_item_image_view);
        photoImageView.setImageDrawable(developer.getProfilePhoto());

        //display the username of the next developer
        TextView usernameTextView = (TextView) listView.findViewById(R.id.list_item_text_view);
        usernameTextView.setText(developer.getUsername());


        return listView;



    }
}
