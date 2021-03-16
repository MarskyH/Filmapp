package com.example.filmapp.dataBase

import androidx.lifecycle.LiveData
import com.example.filmapp.Media.dataBase.FavoritosDAO
import com.example.filmapp.Media.dataBase.FavoritosEntity

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