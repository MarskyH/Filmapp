package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class SeriesDescubraViewModel(val service: Service) : ViewModel() {

    var returnDescubraSeriesListAPI = MutableLiveData<>()

    fun getDescubraSeriesList(){
        viewModelScope.launch {
            returnDescubraSeriesListAPI.value = service.

        }
    }
}