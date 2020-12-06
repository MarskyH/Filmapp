package com.example.filmapp.Entities.Movie



data class BaseMovie(
    val page: Int,
    val results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)