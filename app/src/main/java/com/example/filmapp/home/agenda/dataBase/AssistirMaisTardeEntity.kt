package com.example.filmapp.home.agenda.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assistirmaistardetable")
data class AssistirMaisTardeEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    var poster_path: String,
    var type: String
    )