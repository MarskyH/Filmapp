package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import kotlinx.coroutines.launch

class TemporadaFragmentViewModel(val service: Service) : ViewModel() {


    var listSeasonDetails = MutableLiveData<SeasonDetails>()


    fun getSeasonDetails(serieId: Int, seasonNumber: Int){
        viewModelScope.launch {
            listSeasonDetails.value = service.getSesaonDetails(
                serieId,
                seasonNumber,
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
            )
        }
    }

}
