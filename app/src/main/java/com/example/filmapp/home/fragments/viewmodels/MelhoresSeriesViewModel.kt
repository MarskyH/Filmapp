package com.example.filmapp.home.fragments.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class MelhoresSeriesViewModel(val service: Service) : ViewModel() {

    var returnTopSeriesAPI = MutableLiveData<BaseTv>()

    fun getTopSeriesList(){
        viewModelScope.launch {
            returnTopSeriesAPI.value = service.getTopSeries(
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )

        }
    }

}