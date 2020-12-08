package com.example.filmapp.home.activitys.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class AcompanhandoViewModel(val service: Service) : ViewModel() {

    var returnUserAcompanhandoListAPI = MutableLiveData<BaseTv>()

    fun getUserAcompanhandoList(){
        viewModelScope.launch {
            returnUserAcompanhandoListAPI.value = service.getAcompanhandoList(
                "1",
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US"
            )

        }
    }

}