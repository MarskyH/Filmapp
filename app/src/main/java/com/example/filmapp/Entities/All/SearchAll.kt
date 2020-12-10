package com.example.filmapp.Entities.All

data class BaseSearchAll(
    val page: Int,
    val results: ArrayList<ResultSearchAll>,
    val total_pages: Int,
    val total_results: Int
)

data class ResultSearchAll(
    val adult: Boolean,
    val backdrop_path: Any,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val known_for: List<KnownForSearchAll>,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Any?,
    val poster_path: Any,
    val profile_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Any?,
    val vote_count: Any?
)

data class KnownForSearchAll(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)