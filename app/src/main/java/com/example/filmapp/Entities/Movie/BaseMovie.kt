package com.example.filmapp.Entities.Movie


/*Classe para pegar a raiz de filmes, pode ser usada para pegar os dados dos filmes populares, por exemplo*/
data class BaseMovie(
    val page: Int,
    val results: ArrayList<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)