package com.example.filmapp.home.descubra

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.All.BaseSearchAll
import com.example.filmapp.Entities.All.ResultSearchAll
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class FilmesDescubraViewModel(val service: Service) : ViewModel() {

    var returnAPI = MutableLiveData<BaseMovie>()

    fun getMovieList(name: String?){
        viewModelScope.launch {
            returnAPI.value = service.getSearchMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR",
                name.toString()
            )
        }

    }
}