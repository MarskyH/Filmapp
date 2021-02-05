package com.example.filmapp.Media.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritostable")
data class FavoritosEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    var poster_path: String,
    var type: String
    )