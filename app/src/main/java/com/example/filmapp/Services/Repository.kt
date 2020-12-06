package com.example.filmapp.Services



import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface Service {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseMovie

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseTv

}


val urlApiMovies = "https://api.themoviedb.org/3/"
val keyApi = "4a6baee1eff7d3911f03f59b9b8f43eb"
val popularFilms = "https://api.themoviedb.org/3/movie/popular?api_key=4a6baee1eff7d3911f03f59b9b8f43eb&language=en-US&page=1"

var gson = GsonBuilder()
    .setLenient()
    .create()


val retrofit = Retrofit.Builder()
    .baseUrl(urlApiMovies)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

val service: Service = retrofit.create(Service::class.java)