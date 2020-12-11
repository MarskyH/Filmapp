package com.example.filmapp.home.emAlta

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.All.BaseAll
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class MelhoresDaSemanaViewModel(val service: Service) : ViewModel() {

    var returnMelhoresDaSemanaListAPI = MutableLiveData<BaseAll>()

    fun getMelhoresDaSemanaList(){
        viewModelScope.launch {
            returnMelhoresDaSemanaListAPI.value = service.getTrending(
                "all",
                "week",
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )
        }
    }

}