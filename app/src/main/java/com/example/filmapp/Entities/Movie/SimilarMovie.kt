package com.example.filmapp.Entities.Movie

import java.io.Serializable

/*Classe para pegar os filmes similiares de acordo com um outro filme, olhar classe service*/
data class SimilarMovies(
    val page: Int,
    var results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)


