package com.example.filmapp.Entities.TV



data class BaseTv(
    val resultTvs: ArrayList<ResultTv>,
    val total_results: Int,
    val page: Int,
    val total_pages: Int
)
