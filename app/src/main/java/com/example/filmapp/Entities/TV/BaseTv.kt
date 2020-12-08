package com.example.filmapp.Entities.TV



data class BaseTv(
    val page: Int,
    val results: ArrayList<ResultTv>,
    val total_results: Int,
    val total_pages: Int
)
