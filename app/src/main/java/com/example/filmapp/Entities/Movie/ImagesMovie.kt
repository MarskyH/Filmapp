package com.example.filmapp.Entities.Movie

import java.io.Serializable

/*Classe para pegar as imagens de filmes*/
data class ImagesMovie(
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<Poster>
)

data class Backdrop(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: Any,
    val vote_average: Int,
    val vote_count: Int,
    val width: Int
):Serializable

data class Poster(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String,
    val vote_average: Int,
    val vote_count: Int,
    val width: Int
):Serializable