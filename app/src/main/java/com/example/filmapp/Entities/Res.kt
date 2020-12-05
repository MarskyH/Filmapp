package com.example.filmapp.Entities

data class Res(

    val page: Int,
    val results: ArrayList<Results>,
    val total_results: Int,
    val total_pages: Int
)

data class Results(

    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: ArrayList<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double

)
