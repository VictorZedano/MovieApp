package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.proxy.interfaces.MovieService
import com.example.movieapp.proxy.retrofit.MovieRetrofit
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var responseTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        responseTextView = findViewById(R.id.response_text)

        val service = MovieRetrofit.retrofit.create(MovieService::class.java)
        val call = service.getMovies()
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    val movieNames = movies?.joinToString("\n") { it.title }
                    responseTextView.text = movieNames
                } else {
                    responseTextView.text = "Error en la respuesta: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                responseTextView.text = "Error al realizar la petición: ${t.message}"
            }
        })
    }
}
