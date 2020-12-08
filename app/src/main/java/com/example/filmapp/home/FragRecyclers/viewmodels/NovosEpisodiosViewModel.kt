package com.example.filmapp.home.FragRecyclers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class NovosEpisodiosViewModel(val service: Service) : ViewModel() {

    var returnNovosEpisodiosListAPI = MutableLiveData<>()

    fun getNovosEpisodiosList(){
        viewModelScope.launch {
            returnNovosEpisodiosListAPI.value = service.

        }
    }
}