package com.example.opeyemi.lagosdevelopers;

import android.graphics.drawable.Drawable;

/**
 * Created by opeyemi on 9/13/2017.
 */
public class Developer {

    private Drawable profilePhoto;
    private String username;
    private String gitURL;
    private String name;
    private int repos; //repositories
    private int followers; //number of followers
    private int following; // number of developers followed by this developer

    public Developer (Drawable pPhoto, String user, String gitURL, String name,
                      int repos, int followers, int following ){

        this.gitURL = gitURL;
        this.name = name;
        profilePhoto = pPhoto;
        username = user;
        this.repos = repos;
        this.followers = followers;
        this.following = following;
    }

    public Drawable getProfilePhoto(){
        return profilePhoto;
    }

    public String getUsername(){
        return username;
    }

    public String getGitURL(){
        return gitURL;
    }

    public String getName(){
        return name;
    }

    public int getRepositories(){
        return repos;
    }

    public int getFollowers(){
        return followers;
    }

    public int getFollowings(){
        return following;
    }

}
