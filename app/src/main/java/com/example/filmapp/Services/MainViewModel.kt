package com.example.filmapp.Services


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.TV.BaseTv

import kotlinx.coroutines.launch

class MainViewModel(val service: Service) : ViewModel() {


    var listResMovies = MutableLiveData<BaseMovie>()
    var listResSeries = MutableLiveData<BaseTv>()


    fun getPopularMovies() {
        viewModelScope.launch {
            listResMovies.value = service.getPopularMovies(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
                1
            )

        }
    }
        fun getPopularSeries(){
            viewModelScope.launch {
                listResSeries.value = service.getPopularSeries(
                    "4a6baee1eff7d3911f03f59b9b8f43eb",
                    "en-US",
                    1
                )

            }



        }

}
