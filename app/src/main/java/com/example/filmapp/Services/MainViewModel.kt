package com.example.filmapp.Services


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.Res
import com.example.filmapp.Entities.Results
import kotlinx.coroutines.launch

class MainViewModel(val service: Service) : ViewModel() {


    var listRes = MutableLiveData<Results>()


    fun getAllResults(){
        viewModelScope.launch {
            listRes.value = service.getAllResults(
                "popular",
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
                1
            )

        }


    }

}
