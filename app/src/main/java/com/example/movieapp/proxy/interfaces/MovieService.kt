package com.example.movieapp.proxy.interfaces

import com.example.movieapp.model.MovieListResponse
import com.example.movieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    fun getMovies(): Call<MovieResponse>
}
