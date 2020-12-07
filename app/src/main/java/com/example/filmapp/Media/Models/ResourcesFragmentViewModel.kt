package com.example.filmapp.Media.Models

import com.example.filmapp.Services.Service



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun getImagesMovie(id: String){
        viewModelScope.launch {
            listImagesMovie.value = service.getImagesMovies(
                id,
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
            )
        }
    }
    fun getImagesSerie(id: String){
        viewModelScope.launch {
            listImagensSerie.value = service.getImagesSerie(
                id,
                "a6baee1eff7d3911f03f59b9b8f43eb",
                "en-US",
            )
        }
    }

}
