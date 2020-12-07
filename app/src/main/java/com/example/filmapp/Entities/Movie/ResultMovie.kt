package com.example.filmapp.Entities.Movie

import java.io.Serializable

/*Classe para pegar os resultados de filmes, usado junto com a BaseMovie*/
data class ResultMovie(
    val id : Int,
    val popularity : Double,
    val vote_count : Int,
    val release_date : String,
    val adult : Boolean,
    val backdrop_path : String,
    val title : String,
    val genre_ids : ArrayList<Int>,
    val original_language : String,
    val original_title : String,
    val poster_path : String,
    val overview : String,
    val video : Boolean,
    val vote_average : Double
):Serializable