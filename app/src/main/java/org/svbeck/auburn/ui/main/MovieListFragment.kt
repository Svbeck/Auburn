package org.svbeck.auburn.ui.main

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import org.svbeck.auburn.R
import org.svbeck.auburn.adapters.MovieListAdapter
import org.svbeck.auburn.models.MovieData
import java.net.HttpURLConnection
import java.net.URL

class MovieListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var movieList: MutableList<MovieData>

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.movie_list_fragment, container, false)
        movieDataSet()
        viewManager = LinearLayoutManager(activity)
        viewAdapter = MovieListAdapter(movieList)

        recyclerView = rootView.findViewById<RecyclerView>(R.id.rv_movie_list_fragment).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun movieDataSet() {
        val url = "https://api.themoviedb.org/3/movie/upcoming?api_key=ee1b94905d841b2c9579fb3059012985"
        GetMovies().execute(url)

    }

    inner class GetMovies() : AsyncTask<String, Int, String>() {
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
                Log.d("XXXXXXXXXXXXX",e.toString())
                throw e
            }
        }

        override fun onPostExecute(result: String?) {

            //TODO 4: Bind Movie data to fragment
            //TODO 5: Get poster image from tmdb and polish gui
            //TODO 6: Create fragments for side menu
            lateinit var movieInfo: JSONObject //json object conatining info about movie
            try {

                val jsonObject = JSONObject(result) //get movie results from most popular movies
                val jsonArray = jsonObject.getJSONArray("results") //put objects into array

                for (i in 0..(jsonArray.length() - 1)) { //iterates through length of result
                    movieInfo = jsonArray.getJSONObject(i) //turn each movie to an movie object
                    movieList.add(MovieData(movieInfo))   //Add Json object to Movie
                }

                Toast.makeText(context, movieList.toString(), Toast.LENGTH_LONG).show()

            } catch (e: JSONException) { //Catch errors for json parsing
                Toast.makeText(Application(), e.toString(), Toast.LENGTH_LONG).show()
            }

        }
    }

}
