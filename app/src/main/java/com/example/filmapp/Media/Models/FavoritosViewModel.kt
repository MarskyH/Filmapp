package com.example.filmapp.Media.Models

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.Services.Service
import com.example.filmapp.dataBase.AssistirMaisTardeRepository
import com.example.filmapp.dataBase.FavoritosRepository
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritosViewModel(app: Application): AndroidViewModel(app) {

    val mediaList: LiveData<List<FavoritosEntity>>
    val mediaListSerie: LiveData<List<FavoritosEntity>>
    val mediaListMovie: LiveData<List<FavoritosEntity>>
    private val repository: FavoritosRepository

    init {
        val favoritosDAO = FilmAppDataBase.getDataBase(app).favoritosDAO()
        repository = FavoritosRepository(favoritosDAO)
        mediaList = repository.readAllData
        mediaListMovie = repository.readAllDataMovie
        mediaListSerie = repository.readAllDataSerie
    }

    fun saveNewMedia(media: FavoritosEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveInFavoritosListTask(media)
        }
    }

    fun removeMedia(media: FavoritosEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOfFavoritosListTask(media)
        }
    }

    fun checkMovieInList(listAPI: ArrayList<ResultMovie>, listDataBase: List<FavoritosEntity>): ArrayList<ResultMovie>{
        var listResult = arrayListOf<ResultMovie>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if(media.id == it.id)
                    media.favoritoIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

    fun checkTVInList(listAPI: ArrayList<ResultTv>, listDataBase: List<AssistirMaisTardeEntity>): ArrayList<ResultTv>{
        var listResult = arrayListOf<ResultTv>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if(media.id == it.id)
                    media.favoritosIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

}