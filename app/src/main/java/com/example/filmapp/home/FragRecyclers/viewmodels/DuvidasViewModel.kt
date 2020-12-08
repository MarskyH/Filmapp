package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class DuvidasViewModel(val service: Service) : ViewModel() {

    var returnDuvidas = MutableLiveData<>()
    var returnNovidades = MutableLiveData<>()

    fun getDuvidasList(){
        viewModelScope.launch {
            returnDuvidas.value = service.

        }
    }

    fun getNovidadesList(){
        viewModelScope.launch {
            returnNovidades.value = service.

        }
    }
}