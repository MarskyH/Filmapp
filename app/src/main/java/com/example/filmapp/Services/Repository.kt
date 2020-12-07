package com.example.filmapp.Services


import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ImagesTv
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Service {


    //pega os filmes populares atuais
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseMovie

    //pega os filmes similares, passar um id de filme, o Path.
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): SimilarMovies

    //pega as imagens de um determinado filme, passar um id de filme, o Path.
    @GET("movie/{movie_id}/images")
    suspend fun getImagesMovies(
        @Path("movie_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): ImagesMovie

    //pega as séries populares atuais
    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseTv

    //pega os detalhes de uma série, passar um id de série, o Path
    @GET("tv/{tv_id}")
    suspend fun getDetailsSerie(
        @Path("tv_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): TvDetails

    //pega as imagens de uma determinada série, passar um id de série, o Path.
    @GET("/tv/{tv_id}/images")
    suspend fun getImagesSerie(
        @Path("tv_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): ImagesTv

    //pega os detalhes de uma determinada temporada, passar um id de série e o número da temporada, o Path.
    @GET("/tv/{tv_id}/season/{season_number}")
    suspend fun getSesaonDetails(
        @Path("tv_id") serie_id: String,
        @Path("season_number") season_number: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): SeasonDetails
}


val urlApiMovies = "https://api.themoviedb.org/3/"
val keyApi = "4a6baee1eff7d3911f03f59b9b8f43eb"
val popularFilms =
    "https://api.themoviedb.org/3/movie/popular?api_key=4a6baee1eff7d3911f03f59b9b8f43eb&language=en-US&page=1"

var gson = GsonBuilder()
    .setLenient()
    .create()


val retrofit = Retrofit.Builder()
    .baseUrl(urlApiMovies)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

val service: Service = retrofit.create(Service::class.java)