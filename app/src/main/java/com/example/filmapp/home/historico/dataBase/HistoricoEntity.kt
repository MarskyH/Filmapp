package com.example.filmapp.home.historico.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "historicotable")
data class HistoricoEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    var poster_path: String,
    var type: String,
    var seasonNumber: Int = 0,
    var episodeNumber: Int = 0,
    var formattedTitle: String = "",
    var episodeTitle: String = "",
    var formattedEpisodeTitle: String = "",
    var date: String = ""
)