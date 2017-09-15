package com.example.opeyemi.lagosdevelopers;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DeveloperProfileActivity extends AppCompatActivity {

    private static Developer mDeveloper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_profile);



        //get the image for the developer and display on the profile details activity
        ImageView profileImage = (ImageView) findViewById(R.id.user_imageView);
        profileImage.setImageDrawable(mDeveloper.getProfilePhoto());

        //get the username of the user that was clicked and display on the username textView
        TextView usernameTextView = (TextView) findViewById((R.id.username_textView));
        usernameTextView.setText(mDeveloper.getUsername());

        //get the github url from the developer object passed to this activity
        //and display it in the appropriate textview
        final TextView urlTextView = (TextView) findViewById(R.id.url_textView);
        urlTextView.setText(mDeveloper.getGitURL());

        //get the score from the Developer object passed to this activity from main activity
        //and display it in the appropriate textView
        TextView nameTextView = (TextView) findViewById(R.id.name_textView);
        nameTextView.setText(mDeveloper.getName());

        //get the followers from the Developer object and display at the appropriate textView
        TextView followersTextView = (TextView) findViewById(R.id.followers_text_view);
        followersTextView.setText(new Integer(mDeveloper.getFollowers()).toString());

        //get the follows from the Developer object and display at the appropriate textView
        TextView followingsTextView = (TextView) findViewById(R.id.followings_text_view);
        followingsTextView.setText(new Integer(mDeveloper.getFollowings()).toString());

        //get the number of repositories from the Developer object and display at the appropriate textView
        TextView repositoriesTextView = (TextView) findViewById(R.id.repositories_text_view);
        repositoriesTextView.setText(new Integer(mDeveloper.getRepositories()).toString());


        //setting an action listener for a url click in this activity
        View urlView = findViewById(R.id.url_view);
        urlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDeveloper.getGitURL()));
                    startActivity(myIntent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(DeveloperProfileActivity.this,"No application can handle this " +
                            "request. Please install a web browser", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        //setting a listener for the share button click in this activity
        Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });






    }

    public static void setDeveloper(Developer developer){
        mDeveloper = developer;
    }
}

