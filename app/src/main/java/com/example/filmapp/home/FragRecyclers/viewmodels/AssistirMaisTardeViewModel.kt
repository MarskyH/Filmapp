package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class AssistirMaisTardeViewModel(val service: Service) : ViewModel() {

    var returnUserAssistirMaisTardeAPI = MutableLiveData<>()

    fun getUserAssistirMaisTardeList(){
        viewModelScope.launch {
            returnUserAssistirMaisTardeAPI.value = service.

        }
    }

}