package com.example.filmapp.Entities.Movie

import java.io.Serializable

/*Classe para pegar os resultados de filmes, usado junto com a BaseMovie*/
data class ResultMovie(
    val id : Int,
    val popularity : Double,
    val vote_count : Int,
    var release_date : String,
    val adult : Boolean,
    val backdrop_path : String,
    var title : String,
    var formattedTitle: String,
    val genre_ids : ArrayList<Int>,
    val original_language : String,
    val original_title : String,
    val poster_path : String,
    val overview : String,
    val video : Boolean,
    var vote_average : Double,
    var numberStars: Double,
    var assistirMaisTardeIndication: Boolean = false,
    var watched: Boolean = false,
    var favoritoIndication: Boolean = false
):Serializable