package com.example.filmapp.dataBase

import androidx.lifecycle.LiveData
import com.example.filmapp.Media.dataBase.FavoritosDAO
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeDAO
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.historico.dataBase.HistoricoDAO
import com.example.filmapp.home.historico.dataBase.HistoricoEntity

class AssistirMaisTardeRepository(private val assistirMaisTardeDAO: AssistirMaisTardeDAO){

    val readAllData: LiveData<List<AssistirMaisTardeEntity>> = assistirMaisTardeDAO.getAssistirMaisTardeList()

    suspend fun saveInAssistirMaisTardeListTask(media: AssistirMaisTardeEntity){
        assistirMaisTardeDAO.saveInAssistirMaisTardeList(media)
    }

    suspend fun removeOfAssistirMaisTardeListTask(media: AssistirMaisTardeEntity){
        assistirMaisTardeDAO.removeOfAssistirMaisTardeList(media)
    }

}

class FavoritosRepository(private val favoritosDAO: FavoritosDAO){

    val readAllData: LiveData<List<FavoritosEntity>> = favoritosDAO.getFavoritosList()
    val readAllDataMovie: LiveData<List<FavoritosEntity>> = favoritosDAO.getFavoritosListMovie()
    val readAllDataSerie: LiveData<List<FavoritosEntity>> = favoritosDAO.getFavoritosListSerie()


    suspend fun saveInFavoritosListTask(media: FavoritosEntity){
        favoritosDAO.saveInFavoritosList(media)
    }

    suspend fun removeOfFavoritosListTask(media: FavoritosEntity){
        favoritosDAO.removeOfFavoritosList(media)
    }

}