package com.example.filmapp.home.descubra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Services.Service

class SeriesDescubraViewModel(val service: Service) : ViewModel() {

    var returnDescubraSeriesListAPI = MutableLiveData<BaseTv>()

    fun getDescubraSeriesList(name: String){

//        viewModelScope.launch {
//            returnDescubraSeriesListAPI.value = service.getSearcTv(
//                "4a6baee1eff7d3911f03f59b9b8f43eb",
//                "en-US",
//                name
//            )
//
//        }

    }
}