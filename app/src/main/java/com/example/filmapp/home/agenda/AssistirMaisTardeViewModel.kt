package com.example.filmapp.home.agenda

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Services.Service
import com.example.filmapp.dataBase.AssistirMaisTardeRepository
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssistirMaisTardeViewModel(app: Application): AndroidViewModel(app) {

    val mediaList: LiveData<List<AssistirMaisTardeEntity>>
    private val repository: AssistirMaisTardeRepository

    init {
        val assistirMaisTardeDAO = FilmAppDataBase.getDataBase(app).assistirMaisTardeDao()
        repository = AssistirMaisTardeRepository(assistirMaisTardeDAO)
        mediaList = repository.readAllData
    }

    fun saveNewMedia(media: AssistirMaisTardeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveInAssistirMaisTardeListTask(media)
        }
    }

    fun removeMedia(media: AssistirMaisTardeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOfAssistirMaisTardeListTask(media)
        }
    }

    fun checkMovieInList(listAPI: ArrayList<ResultMovie>, listDataBase: List<AssistirMaisTardeEntity>): ArrayList<ResultMovie>{
        var listResult = arrayListOf<ResultMovie>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if(media.id == it.id)
                    media.assistirMaisTardeIndication = true
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
                    media.assistirMaisTardeIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

}