package com.example.filmapp.Media.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface FavoritosDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInFavoritosList(media: FavoritosEntity)

    @Query("SELECT * FROM favoritostable")
    fun getFavoritosList(): LiveData<List<FavoritosEntity>>


    @Query("SELECT * FROM favoritostable WHERE type == 'Tv'")
    fun getFavoritosListSerie(): LiveData<List<FavoritosEntity>>

    @Query("SELECT * FROM favoritostable WHERE type == 'Movie'")
    fun getFavoritosListMovie(): LiveData<List<FavoritosEntity>>

    @Delete
    suspend fun removeOfFavoritosList(media: FavoritosEntity)

//    @Query("SELECT * FROM favoritostable WHERE id = :id")
//    fun checkInList(id: Int): FavoritosEntity
}