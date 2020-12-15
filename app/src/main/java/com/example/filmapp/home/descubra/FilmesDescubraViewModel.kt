package com.example.filmapp.home.descubra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Services.Service

class FilmesDescubraViewModel(val service: Service) : ViewModel() {

    var returnDescubraFilmesListAPI = MutableLiveData<BaseMovie>()

    fun getDescubraFilmesList(name: String){
//        viewModelScope.launch {
//            returnDescubraFilmesListAPI.value = service.getSearchMovies(
//                "4a6baee1eff7d3911f03f59b9b8f43eb",
//                "en-US",
//                name
//            )
//        }
    }
}