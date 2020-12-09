package com.example.filmapp.Entities.TV

import java.io.Serializable

/*Classe para pegar os resultados de filmes, usado junto com a BaseTV*/
data class ResultTv(
    val id : Int,
    val popularity : Double,
    val name : String,
    val original_name : String,
    val first_air_date : String,
    val backdrop_path : String,
    val vote_average : Double,
    val genre_ids : ArrayList<Int>,
    val overview : String,
    val original_language : String,
    val vote_count : Int,
    val poster_path : String,
    val origin_country : ArrayList<String>
): Serializable