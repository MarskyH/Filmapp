package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Res
import com.example.filmapp.Entities.Results
import kotlinx.coroutines.launch

class MediaViewModel(val service: Service) : ViewModel() {


    var listRes = MutableLiveData<Res>()


    fun getAllResultsSeries(category: String){
        viewModelScope.launch {
            listRes.value = service.getAllResultsSeries(
                category,
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
                1
            )

        }


    }

}
