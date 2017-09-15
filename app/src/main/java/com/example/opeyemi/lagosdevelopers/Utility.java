package com.example.opeyemi.lagosdevelopers;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by opeyemi on 9/13/2017.
 */
public class Utility {


    private static final String LOG_TAG = Utility.class.getSimpleName();

    /**
     * private constructor with no implementation to make sure
     * the Utility class cannot be instantiated
     */
    private Utility() {

    }


    /**
     * Method to process the api url and return an arraylist of Developers
     * using other utility method
     * @param APIurl
     * @return
     */
    public static ArrayList<Developer> extractDevelopers(String APIurl) {


        String jsonResponse = null;
        ArrayList<Developer> developers = null;

        try {
            URL url = makeURL(APIurl);
            jsonResponse = makeHttpRequest(url);
            if (jsonResponse != null) {
                developers = extractFromJSON(jsonResponse);
            } else {
                Log.e(LOG_TAG, "the http request returns a null string");
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "error while making http request", e);
        }

        return developers;
    }

    /**
     * method to extract required developer features from the jsonresponse
     * @param jsonResponse
     * @return ArrayList<Developer>
     */
    private static ArrayList<Developer> extractFromJSON(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList developers = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of Developer objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray developersArray = root.getJSONArray("items");




            for (int i = 0; i < developersArray.length(); i++) {
                //get the current object containing the developers data from the developers array
                JSONObject currentDeveloper = developersArray.getJSONObject(i);

                //to access the the full details of each developer
                //retrieve more details using the api string contained in the value of key url
                String fullApi = currentDeveloper.getString("url");
                URL userApiUrl = makeURL(fullApi);
                String newJsonResponse = makeHttpRequest(userApiUrl);
                JSONObject newRoot = new JSONObject(newJsonResponse);



                //get the username, profile picture url, github url and score from the current object
                String username = newRoot.getString("login");
                String pictureUrl = newRoot.getString("avatar_url");
                String gitHubUrl = newRoot.getString("html_url");
                String fullName = newRoot.getString("name");
                int repositories = newRoot.getInt("public_repos");
                int followers = newRoot.getInt("followers");
                int following = newRoot.getInt("following");

                //get each developers drawable object from the picture url gotten from the json response
                URL imageURL = new URL(pictureUrl);
                InputStream content = (InputStream)imageURL.getContent();
                Drawable d = Drawable.createFromStream(content, "src");


                //create a new developer object and add it to the array list
                developers.add(new Developer(d, username,gitHubUrl, fullName,repositories,followers,following));
            }

        } catch (JSONException e) {
            // catch any error that might occur while analysing the JSON response and log it
            Log.e(LOG_TAG, "Problem parsing the developer JSON results", e);
        }catch (MalformedURLException e){

            Log.e(LOG_TAG, "Problem forming the image url", e);
        }catch (IOException e){
            Log.e(LOG_TAG, "error while retrieving image", e);
        }

        return developers;

    }


    /**
     * method that create a URL object form string objects
     * @param url
     * @return makeURL
     */
    public static URL makeURL(String url) {

        URL urlString = null;
        try {
            urlString = new URL(url);
        } catch (MalformedURLException e) {

            Log.e(LOG_TAG, "error while forming url", e);

        }
        return urlString;

    }

    /**
     * method that helps in making a connection to the internet using a URL object
     * @param url
     * @return String jsonResponse
     * @throws IOException
     */
    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /*milliseconds*/);
            connection.setConnectTimeout(50000 /*milliseconds*/);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Unhandled response code from the connection:" + connection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "error while making Http connection or parsing inputStream", e);

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.v(LOG_TAG, "error while closing connection", e);
                }
            }
        }


        return jsonResponse;

    }

    /**
     * method that accepts an InputStream object analyses it and return
     * coressponding String object
     * @param input
     * @return String jsonResponse
     * @throws IOException
     */
    public static String readInputStream(InputStream input) throws IOException {

        // a string builder object to hold the output from analysed input stream
        StringBuilder readJsonResponse = new StringBuilder();

        if (input != null) {
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                readJsonResponse.append(readLine);
                readLine = bufferedReader.readLine();

            }
        }


        return readJsonResponse.toString();
    }

}
