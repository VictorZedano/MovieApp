package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.Genres
import com.example.movieapp.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<Movie>, private val genres: List<Genres>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        val movieNameTextView: TextView = itemView.findViewById(R.id.movieNameTextView)
        val movieGenresTextView: TextView = itemView.findViewById(R.id.movieGenresTextVIew)
        val movieVoteTextView: TextView = itemView.findViewById(R.id.movieVoteTextView)
        val movieVoteCount: TextView = itemView.findViewById(R.id.movieVoteCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieNameTextView .text = movie.title
        holder.movieGenresTextView.text = getGenresString(movie.genreIds)
        holder.movieVoteTextView.text = movie.voteAverage.toString()
        holder.movieVoteCount.text = movie.voteCount.toString()
        val currentMovie = movies[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w300_and_h450_bestv2/"+currentMovie.posterPath).into(holder.movieImageView)
        holder.movieNameTextView.text = currentMovie.title
    }
    private fun getGenresString(genreIds: List<Int>): String {
        val genreNames = mutableListOf<String>()
        for (genreId in genreIds) {
            val genre = genres.find { it.id == genreId }
            genre?.let {
                genreNames.add(it.name)
            }
        }
        return genreNames.joinToString(", ")
    }

    override fun getItemCount() = movies.size
}