package com.example.filmapp.Entities.TV


/*Classe para pegar a raiz de séries, pode ser usada para pegar os dados das séries populares, por exemplo*/
data class BaseTv(
    val page: Int,
    val results: ArrayList<ResultTv>,
    val total_results: Int,
    val total_pages: Int
)
