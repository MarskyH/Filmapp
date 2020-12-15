package com.example.filmapp.home.melhores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class MelhoresFilmesViewModel(val service: Service) : ViewModel() {

    var returnTopMoviesAPI = MutableLiveData<BaseMovie>()

    fun getTopMoviesList(){
        viewModelScope.launch {
            returnTopMoviesAPI.value = service.getTopMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )

        }
    }
}