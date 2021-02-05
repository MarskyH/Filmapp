package com.example.filmapp.home.acompanhando.realtimeDatabase

import com.example.filmapp.Media.dataBase.WatchedEpisodeScope

data class AcompanhandoScope(
    var id: Int,
    var title: String,
    var poster_path: String,
    var number_of_episodes: Int = 0,
    var number_of_seasons: Int = 0,
    var lastEpisode: Int = 0,
    var nextEpisodeTitle: String = "",
    var nextEpisodeNumber: Int = 1,
    var totalEpisodesWatched: Int = 0,
    var currentSeason: Int = 1,
    var userProgress: Int = 0,
    var finished: Int = 0,
    var watchedEpisodes: ArrayList<WatchedEpisodeScope> = arrayListOf()
)

//Em relação ao parâmetro finished:
//0 = false
//1 = true