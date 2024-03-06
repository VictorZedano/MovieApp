package com.example.movieapp.proxy.retrofit

import com.example.movieapp.proxy.interfaces.MovieService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRetrofit {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMDcwYzE3YzBhMmQ2OTZkNmQ0NTg4MGRhMzc0NjdkZCIsInN1YiI6IjY1ZTU1MWYyOTQ1MWU3MDE4NzVjMGFmOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-O-akM-4clxoK6c5lYVqCfBlkpB3wGIK05YFyZJ-0hU"

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer $AUTH_TOKEN")
            val request = requestBuilder.build()

            chain.proceed(request)
        }
    }.build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}


