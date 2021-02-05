package com.example.filmapp.home.historico.realtimeDatabase

data class HistoricoScope(
    val id: Int,
    var title: String,
    var poster_path: String,
    var type: String,
    var seasonNumber: Int = 0,
    var episodeNumber: Int = 0,
    var formattedTitle: String = "",
    var episodeTitle: String = "",
    var formattedEpisodeTitle: String = "",
    var date: String = "",
    var episodeId: Int = 0
)