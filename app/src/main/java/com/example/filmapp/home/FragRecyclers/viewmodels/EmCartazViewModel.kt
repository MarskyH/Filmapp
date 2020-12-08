package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class EmCartazViewModel(val service: Service) : ViewModel() {

    var returnEmCartazAPI = MutableLiveData<>()

    fun getEmCartazList(){
        viewModelScope.launch {
            returnEmCartazAPI.value = service.

        }
    }
}