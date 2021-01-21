package com.example.filmapp.home.historico.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity

@Dao
interface HistoricoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInHistorico(media: HistoricoEntity)

    @Query("SELECT * FROM historicotable")
    fun getHistorico(): LiveData<List<HistoricoEntity>>

    @Delete
    suspend fun removeOfHistorico(media: HistoricoEntity)
}