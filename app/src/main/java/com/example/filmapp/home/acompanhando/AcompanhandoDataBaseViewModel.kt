package com.example.filmapp.home.acompanhando

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Services.Service
import com.example.filmapp.dataBase.AcompanhandoRepository
import com.example.filmapp.dataBase.AssistirMaisTardeRepository
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AcompanhandoDataBaseViewModel(app: Application) : AndroidViewModel(app) {

    val mediaList: LiveData<List<AcompanhandoEntity>>
    private val repository: AcompanhandoRepository

    init {
        val acompanhandoDAO = FilmAppDataBase.getDataBase(app).acompanhandoDao()
        repository = AcompanhandoRepository(acompanhandoDAO)
        mediaList = repository.readAllData
    }

    fun saveNewItem(media: AcompanhandoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveInAcompanhandoListListTask(media)
        }
    }

    fun removeItem(media: AcompanhandoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOfAcompanhandoListTask(media)
        }
    }

    fun checkSerieInList(
        listAPI: ArrayList<ResultTv>,
        listDataBase: List<AcompanhandoEntity>
    ): ArrayList<ResultTv> {
        var listResult = arrayListOf<ResultTv>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id)
                    media.followingStatusIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

}