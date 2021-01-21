package com.example.filmapp.home.acompanhando.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AcompanhandoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInAcompanhandoList(media: AcompanhandoEntity)

    @Query("SELECT * FROM acompanhandotable")
    fun getAcompanhandoList(): LiveData<List<AcompanhandoEntity>>

    @Delete
    suspend fun removeOfAcompanhandoList(media: AcompanhandoEntity)

}