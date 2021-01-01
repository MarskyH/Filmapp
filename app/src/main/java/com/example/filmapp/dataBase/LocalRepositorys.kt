package com.example.filmapp.dataBase

import androidx.lifecycle.LiveData
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeDAO
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity

class AssistirMaisTardeRepository(private val assistirMaisTardeDAO: AssistirMaisTardeDAO){

    val readAllData: LiveData<List<AssistirMaisTardeEntity>> = assistirMaisTardeDAO.getAssistirMaisTardeList()

    suspend fun saveInAssistirMaisTardeListTask(media: AssistirMaisTardeEntity){
        assistirMaisTardeDAO.saveInAssistirMaisTardeList(media)
    }

    suspend fun removeOfAssistirMaisTardeListTask(media: AssistirMaisTardeEntity){
        assistirMaisTardeDAO.removeOfAssistirMaisTardeList(media)
    }

}