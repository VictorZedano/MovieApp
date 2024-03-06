package com.example.movieapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.model.Genres
import com.example.movieapp.model.GenresResponse
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.proxy.interfaces.GenresService
import com.example.movieapp.proxy.interfaces.MovieService
import com.example.movieapp.proxy.retrofit.MovieRetrofit
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var genres: List<Genres>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.movieRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchGenres()
    }

    private fun fetchGenres() {
        val genresService = MovieRetrofit.retrofit.create(GenresService::class.java)
        val gcall = genresService.getGeners()
        gcall.enqueue(object : Callback<GenresResponse> {
            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful) {
                    genres = response.body()?.genres ?: emptyList()
                    displayGenres()

                    fetchMovies()
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                Log.e(TAG, "Error al realizar la petición: ${t.message}")
            }
        })
    }

    private fun displayGenres() {
        val genresContainer = findViewById<LinearLayout>(R.id.genresContainer)
        for (genre in genres) {
            val button = Button(this@MainActivity)
            button.text = genre.name
            button.setOnClickListener {
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            button.layoutParams = params
            genresContainer.addView(button)
        }
    }
    private fun fetchMovies() {
        val service = MovieRetrofit.retrofit.create(MovieService::class.java)
        val call = service.getMovies()
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    val adapter = MovieAdapter(movies, genres)

                    recyclerView.adapter = adapter
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "Error al realizar la petición: ${t.message}")
            }
        })
    }
}