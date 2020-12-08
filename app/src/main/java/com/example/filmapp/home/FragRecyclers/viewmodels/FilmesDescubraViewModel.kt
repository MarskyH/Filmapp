package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class FilmesDescubraViewModel(val service: Service) : ViewModel() {

    var returnDescubraFilmesListAPI = MutableLiveData<>()

    fun getDescubraFilmesList(){
        viewModelScope.launch {
            returnDescubraFilmesListAPI.value = service.

        }
    }
}