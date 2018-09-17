package org.svbeck.auburn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.svbeck.auburn.R
import org.svbeck.auburn.models.MovieData

class MovieListAdapter(private val movies: List<MovieData>):RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    inner class ViewHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        var movieTitle: TextView = cardView.findViewById(R.id.tv_movie_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_fragment, parent, false) as CardView

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].title
    }

    override fun getItemCount(): Int = movies.size
}