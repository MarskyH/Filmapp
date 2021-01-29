package com.example.filmapp.dataBase

import androidx.lifecycle.LiveData
import com.example.filmapp.Media.dataBase.FavoritosDAO
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoDAO
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
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

class AcompanhandoRepository(private val acompanhandoDAO: AcompanhandoDAO){

    val readAllData: LiveData<List<AcompanhandoEntity>> = acompanhandoDAO.getAcompanhandoList()

    suspend fun saveInAcompanhandoListListTask(media: AcompanhandoEntity){
        acompanhandoDAO.saveInAcompanhandoList(media)
    }

    suspend fun removeOfAcompanhandoListTask(media: AcompanhandoEntity){
       acompanhandoDAO.removeOfAcompanhandoList(media)
    }

}

class HistoricoRepository(private val historicoDAO: HistoricoDAO){

    val readAllData: LiveData<List<HistoricoEntity>> = historicoDAO.getHistorico()

    suspend fun saveInHistoricoTask(media: HistoricoEntity){
        historicoDAO.saveInHistorico(media)
    }

    suspend fun removeOfHistoricoTask(media: HistoricoEntity){
        historicoDAO.removeOfHistorico(media)
    }

}