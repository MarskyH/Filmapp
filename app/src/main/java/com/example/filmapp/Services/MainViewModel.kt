package com.example.filmapp.Services


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.MovieDetails
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.TvDetails

import kotlinx.coroutines.launch

class MainViewModel(val service: Service) : ViewModel() {


    var listResMovies = MutableLiveData<BaseMovie>()
    var listResSeries = MutableLiveData<BaseTv>()
    var listDetailsSeries = MutableLiveData<TvDetails>()
    var listDetailsMovies = MutableLiveData<MovieDetails>()
    var config = MutableLiveData<Config>()


    fun getPopularMovies() {
        viewModelScope.launch {
            listResMovies.value = service.getPopularMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR",
                1
            )

        }
    }

    fun getPopularSeries() {
        viewModelScope.launch {
            listResSeries.value = service.getPopularSeries(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR",
                1
            )

        }
    }

    fun getConfig() {
        viewModelScope.launch {
            config.value = service.getApiConfig(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
            )
        }
    }

    fun getTvDetails(id: Int) {
        viewModelScope.launch {
            listDetailsSeries.value = service.getDetailsSerie(
                id.toString(),
                API_KEY,
                LANGUAGE,
                1
            )

        }
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            listDetailsMovies.value = service.getDetailsMovie(
                id.toString(),
                API_KEY,
                LANGUAGE,
                1
            )

        }
    }


}
