package com.example.filmapp.Entities.All

import java.io.Serializable

data class ResultAll(
    val id: Int,
    var poster_path: String,
    val original_title: String,
    var title: String
): Serializable