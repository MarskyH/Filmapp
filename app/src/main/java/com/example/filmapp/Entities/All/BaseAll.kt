package com.example.filmapp.Entities.All

data class BaseAll(
    val page: Int,
    val results: ArrayList<ResultAll>,
    val total_pages: Int,
    val total_results: Int
)