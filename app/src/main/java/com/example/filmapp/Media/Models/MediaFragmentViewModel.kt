package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.TV.BaseTv
import kotlinx.coroutines.launch

class MediaFragmentViewModel(val service: Service) : ViewModel() {


    var listRes = MutableLiveData<BaseTv>()
    var config = MutableLiveData<Config>()


    fun getAllResultsSeries(category: String){
        viewModelScope.launch {
            listRes.value = service.getPopularSeries(
                API_KEY,
                LANGUAGE,
                1
            )
        }
    }
    fun getConfigAPI(){
        viewModelScope.launch {
            config.value = service.getApiConfig(
                API_KEY
            )
        }
    }


}
