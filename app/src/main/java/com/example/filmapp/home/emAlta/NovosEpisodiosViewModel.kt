package com.example.filmapp.home.emAlta

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.TV.LatestTv
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class NovosEpisodiosViewModel(val service: Service) : ViewModel() {

    var returnNovosEpisodiosListAPI = MutableLiveData<LatestTv>()

    fun getNovosEpisodiosList(){
        viewModelScope.launch {
            returnNovosEpisodiosListAPI.value = service.getLatest(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )
        }
    }
}