package com.example.filmapp.home.agenda.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AssistirMaisTardeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInAssistirMaisTardeList(media: AssistirMaisTardeEntity)

    @Query("SELECT * FROM assistirmaistardetable")
    fun getAssistirMaisTardeList(): LiveData<List<AssistirMaisTardeEntity>>

    @Delete
    suspend fun removeOfAssistirMaisTardeList(media: AssistirMaisTardeEntity)
}