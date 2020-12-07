package com.example.filmapp.Entities.Movie



data class BaseMovie(
    val page: Int,
    val results: ArrayList<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)