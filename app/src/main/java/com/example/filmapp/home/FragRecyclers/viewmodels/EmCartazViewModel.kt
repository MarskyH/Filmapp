package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class EmCartazViewModel(val service: Service) : ViewModel() {

    var returnEmCartazAPI = MutableLiveData<BaseMovie>()

    fun getEmCartazList(){
        viewModelScope.launch {
            returnEmCartazAPI.value = service.getUpcomingMovies(
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )
        }
    }
}