package com.example.movieapp.proxy.interfaces

import com.example.movieapp.model.GenresResponse
import retrofit2.Call
import retrofit2.http.GET

interface GenresService {
    @GET("genre/movie/list?language=es")
    fun getGeners(): Call<GenresResponse>
}