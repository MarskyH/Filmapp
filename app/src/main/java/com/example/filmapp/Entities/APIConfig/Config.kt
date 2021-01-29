package com.example.filmapp.Entities.APIConfig

data class Config(
    val change_keys: List<String> = arrayListOf(),
    val images: Images = Images()
)

data class Images(
    val backdrop_sizes: List<String> = arrayListOf(),
    val base_url: String = "",
    val logo_sizes: List<String> = arrayListOf(),
    val poster_sizes: List<String> = arrayListOf(),
    val profile_sizes: List<String> = arrayListOf(),
    val secure_base_url: String = "",
    val still_sizes: List<String> = arrayListOf()
)

const val API_KEY = "4a6baee1eff7d3911f03f59b9b8f43eb"
const val LANGUAGE = "pt-BR"
const val URL_IMAGE = "https://image.tmdb.org/t/p/w500"
const val ExcpetionTitle = "Título Indisponível"
