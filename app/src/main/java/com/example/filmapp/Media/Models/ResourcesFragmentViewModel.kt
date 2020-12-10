package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.ImagesMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ImagesTv
import com.example.filmapp.Entities.TV.TvDetails
import kotlinx.coroutines.launch

class ResourcesFragmentViewModel(val service: Service) : ViewModel() {


    var listImagesMovie = MutableLiveData<ImagesMovie>()
    var listImagensSerie = MutableLiveData<ImagesTv>()
    var config = MutableLiveData<Config>()

    fun getImagesMovie(id: String){
        viewModelScope.launch {
            listImagesMovie.value = service.getImagesMovies(
                id,
                API_KEY,
                LANGUAGE,
            )
        }
    }
    fun getImagesSerie(id: String){
        viewModelScope.launch {
            listImagensSerie.value = service.getImagesSerie(
                id,
                API_KEY,
                LANGUAGE,
            )
        }
    }
    fun getConfig(){
        viewModelScope.launch {
            config.value = service.getApiConfig(
                API_KEY,
            )
        }
    }

}
