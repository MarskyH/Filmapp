package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.TV.BaseTv
import kotlinx.coroutines.launch

class MediaViewModel(val service: Service) : ViewModel() {


    var listRes = MutableLiveData<BaseTv>()


    fun getAllResultsSeries(category: String){
        viewModelScope.launch {
            listRes.value = service.getPopularSeries(
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
                1
            )

        }


    }

}
