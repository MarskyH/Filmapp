package com.example.filmapp.Media.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInFavoritosList(media: FavoritosEntity)

    @Query("SELECT * FROM favoritostable")
    fun getFavoritosList(): LiveData<List<FavoritosEntity>>

    @Delete
    suspend fun removeOfFavoritosList(media: FavoritosEntity)

//    @Query("SELECT * FROM favoritostable WHERE id = :id")
//    fun checkInList(id: Int): FavoritosEntity
}