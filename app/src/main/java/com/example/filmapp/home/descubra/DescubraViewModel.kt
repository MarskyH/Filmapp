package com.example.filmapp.home.descubra

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.All.BaseSearchAll
import com.example.filmapp.Entities.All.ResultSearchAll
import com.example.filmapp.Entities.Movie.SearchMovie
import com.example.filmapp.Entities.TV.SearchTv
import com.example.filmapp.Services.Service
import kotlinx.coroutines.launch

class DescubraViewModel(val service: Service) : ViewModel() {

//    var returnSearchListAPI = MutableLiveData<ArrayList<ResultSearchAll>>()
//    var returnAPI = MutableLiveData<BaseSearchAll>()
    var listDescubraMovie = MutableLiveData<SearchMovie>()
    var listDescubraTv = MutableLiveData<SearchTv>()
    var config = MutableLiveData<Config>()

    fun getSearchMovie(text: String?){
        viewModelScope.launch {
            listDescubraMovie.value = service.getSearchMovies(
                API_KEY,
                LANGUAGE,
                text.toString()
            )
        }
    }

    fun getSearchTV(text: String?){
        viewModelScope.launch {
            listDescubraTv.value = service.getSearcTv(
                API_KEY,
                LANGUAGE,
                text.toString()
            )
        }
    }

    fun getConfig(){
        viewModelScope.launch {
            config.value = service.getApiConfig(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
            )
        }
    }


//    fun getSearchList(name: String){
//        var listFilter = arrayListOf<ResultSearchAll>()
//
//        viewModelScope.launch {
//            returnAPI.value = service.getSearch(
//                "4a6baee1eff7d3911f03f59b9b8f43eb",
//                name
//            )
//
//            Log.i("NAME", name)
//        }
//
//        var list = returnAPI.value?.results
//        var result = returnAPI.value
//
//        if (list != null) {
//            for (item in list){
//                if (item.media_type != "person"){
//                    listFilter.add(item)
//                }
//            }
//        }
//
//        Log.i("listFilter", listFilter.size.toString())
//        Log.i("list", list?.size.toString())
//        Log.i("result", result.toString())
//        returnSearchListAPI.value = listFilter
//    }
}