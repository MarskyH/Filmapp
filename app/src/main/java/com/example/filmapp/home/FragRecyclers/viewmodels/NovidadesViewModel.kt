package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class NovidadesViewModel(val service: Service) : ViewModel() {

    var returnNovidadesListAPI = MutableLiveData<>()

    fun getNovidadesList(){
        viewModelScope.launch {
            returnNovidadesListAPI.value = service.

        }
    }
}