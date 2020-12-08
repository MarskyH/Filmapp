package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class MelhoresDaSemanaViewModel(val service: Service) : ViewModel() {

    var returnMelhoresDaSemanaListAPI = MutableLiveData<>()

    fun getMelhoresDaSemanaList(){
        viewModelScope.launch {
            returnMelhoresDaSemanaListAPI.value = service.

        }
    }
}