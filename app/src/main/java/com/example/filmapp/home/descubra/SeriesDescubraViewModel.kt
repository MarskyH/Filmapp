package com.example.filmapp.home.descubra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class SeriesDescubraViewModel(val service: Service) : ViewModel() {

    var returnAPI = MutableLiveData<BaseTv>()

    fun getTVList(name: String?){
        viewModelScope.launch {
            returnAPI.value = service.getSearchTv(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR",
                name.toString()
            )
        }

    }
}