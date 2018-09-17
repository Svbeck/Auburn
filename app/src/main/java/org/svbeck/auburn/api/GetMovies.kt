package org.svbeck.auburn.api

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import org.svbeck.auburn.models.MovieData
import java.net.HttpURLConnection
import java.net.URL

class GetMovies() : AsyncTask<String, Int, String>() {
    override fun onPreExecute() {
    }

    override fun doInBackground(vararg params: String?): String {

        return try {
            val url = URL(params[0])    //url string from params
            val httpURLConnection = url.openConnection() as HttpURLConnection //type of http connection
            httpURLConnection.requestMethod = "GET" //get method
            httpURLConnection.connect() //connect to webpage
            httpURLConnection.inputStream.bufferedReader().readText() //read json as string and returns

        } catch (e: Exception) { //catch possible exceptions
            e.printStackTrace()
            throw e
        }
    }

    override fun onPostExecute(result: String?) {
        //TODO 2: Get json data from tmdb and put it in a class
        //TODO 3: Create Fragment and/or recyclerView https://developer.android.com/guide/topics/ui/layout/recyclerview#kotlin
        //TODO 4: Bind Movie data to fragment
        //TODO 5: Get poster image from tmdb and polish gui
        //TODO 6: Create fragments for side menu
        lateinit var movieInfo: JSONObject //json object conatining info about movie
        try {
            val movieList = mutableListOf<MovieData>()
            val jsonObject = JSONObject(result) //get movie results from most popular movies
            val jsonArray = jsonObject.getJSONArray("results") //put objects into array

            for (i in 0..(jsonArray.length() - 1)) { //iterates through length of result
                movieInfo = jsonArray.getJSONObject(i) //turn each movie to an movie object
                movieList.add(MovieData(movieInfo))   //Add Json object to Movie
            }

        } catch (e: JSONException) { //Catch errors for json parsing
            Toast.makeText(Application(), e.toString(), Toast.LENGTH_LONG).show()
        }

    }
}