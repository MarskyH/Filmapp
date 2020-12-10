package com.example.filmapp.Services


import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.All.BaseAll
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.*
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
    @GET("tv/{tv_id}/images")
    suspend fun getImagesSerie(
        @Path("tv_id") id: String,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): ImagesTv

    //PACOTE HOME

    //Retorna os Melhores da Semana
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediatype: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseAll

    //Retorna os filmes Em Cartaz
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseMovie

    //Retorna os Novos Episodios das séries
    @GET("tv/latest")
    suspend fun getLatest(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): LatestTv

    //Retorna os melhores filmes (Classificação)
    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseMovie

    //Retorna as melhores séries (Classificação)
    @GET("tv/top_rated")
    suspend fun getTopSeries(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): BaseTv

    //Pesquisa - Movies
    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseMovie

    //Pesquisa - TV
    @GET("search/tv")
    suspend fun getSearcTv(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseTv

    //pega os detalhes de uma determinada temporada, passar um id de série e o número da temporada, o Path.
    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSesaonDetails(
        @Path("tv_id") id: Int,
        @Path("season_number") number: Int,
        @Query("api_key") key: String,
        @Query("language") language: String,
    ): SeasonDetails

    //Segundo a API, isso configura a parte de imagens da API
    @GET("configuration")
    suspend fun getApiConfig(
        @Query("api_key") key: String,
    ): Config

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