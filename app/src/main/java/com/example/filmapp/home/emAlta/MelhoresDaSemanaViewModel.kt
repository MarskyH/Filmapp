package com.example.filmapp.home.emAlta

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.All.BaseAll
import com.example.filmapp.Entities.All.ResultAll
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class MelhoresDaSemanaViewModel(val service: Service) : ViewModel() {

    var returnMelhoresDaSemanaListAPI = MutableLiveData<ArrayList<ResultAll>>()
    var returnAPI = MutableLiveData<BaseAll>()

    fun getMelhoresDaSemanaList() {
        viewModelScope.launch {
            returnAPI.value = service.getTrending(
                "tv",
                "week",
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR"
            )

            var list = returnAPI.value!!.results

            //Verificando se o retorno da API possui o título
            list.forEach {
                if (it.title == null) {
                    it.title = service.getDetailsSerie(
                        it.id.toString(),
                        API_KEY,
                        LANGUAGE,
                        1
                    ).name
                }
            }

            returnMelhoresDaSemanaListAPI.value = list
        }
    }
}