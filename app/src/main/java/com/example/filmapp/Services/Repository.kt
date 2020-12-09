package com.example.filmapp.Services


import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ImagesTv
import com.example.filmapp.Entities.TV.LatestTv
import com.example.filmapp.Entities.TV.TvDetails
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Service {


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseMovie

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): SimilarMovies

    @GET("movie/{movie_id}/images")
    suspend fun getImagesMovies(
        @Path("movie_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): ImagesMovie

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): BaseTv

    @GET("tv/{tv_id}")
    suspend fun getDetailsSerie(
        @Path("tv_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): TvDetails

    @GET("/tv/{tv_id}/images")
    suspend fun getImagesSerie(
        @Path("tv_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): ImagesTv

    //PACOTE HOME

    //Retorna os melhores filmes (Classificação)
    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseMovie

    //Retorna os filmes Em Cartaz
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseMovie

    //Retorna as melhores séries (Classificação)
    @GET("tv/top_rated")
    suspend fun getTopSeries(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseTv

    //Retorna os Novos Episodios das séries
    @GET("tv/latest")
    suspend fun getLatest(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): LatestTv

    //Retorna a lista de TvShows que o usuário está acompanhando
    @GET("account/{account_id}/favorite/tv")
    suspend fun getAcompanhandoList(
        @Path("account_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): BaseTv


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