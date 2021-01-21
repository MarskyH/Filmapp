package com.example.filmapp.home.acompanhando.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acompanhandotable")
data class AcompanhandoEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    var poster_path: String,
    var number_of_episodes: Int = 0,
    var lastEpisode: Int = 0,
    var nextEpisodeTitle: String = "",
    var nextEpisodeNumber: Int = 1,
    var totalEpisodesWatched: Int = 0,
    var number_of_seasons: Int = 0,
    var currentSeason: Int = 1,
    var userProgress: Int = 0,
    var finished: Boolean = false
)