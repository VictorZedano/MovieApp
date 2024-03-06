package com.example.movieapp.proxy.retrofit

import com.example.movieapp.proxy.interfaces.MovieService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRetrofit {
    private val authorizationInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMDcwYzE3YzBhMmQ2OTZkNmQ0NTg4MGRhMzc0NjdkZCIsInN1YiI6IjY1ZTU1MWYyOTQ1MWU3MDE4NzVjMGFmOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-O-akM-4clxoK6c5lYVqCfBlkpB3wGIK05YFyZJ-0hU")
            .build()
        chain.proceed(newRequest)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authorizationInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: MovieService = retrofit.create(MovieService::class.java)
}

