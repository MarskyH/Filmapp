package com.example.filmapp.Services


import com.example.filmapp.Entities.Res
import com.example.filmapp.Entities.Results

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Service {


    @GET("/3/movie/{category}")
    suspend fun getAllResults(
        @Path("category") category: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Res

    @GET("/3/tv/{category}")
    suspend fun getAllResultsSeries(
        @Path("category") category: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Res

}

val urlApiMovies = "https://developers.themoviedb.org"
val keyApi = "4a6baee1eff7d3911f03f59b9b8f43eb"
val popularFilms = "https://api.themoviedb.org/3/movie/popular?api_key=4a6baee1eff7d3911f03f59b9b8f43eb&language=en-US&page=1"


var gson = GsonBuilder()
    .setLenient()
    .create()

val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(urlApiMovies)
    .build()

val service: Service = retrofit.create(Service::class.java)