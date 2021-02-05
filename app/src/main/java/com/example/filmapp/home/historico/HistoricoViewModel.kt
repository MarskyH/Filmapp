package com.example.filmapp.home.historico

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.dataBase.HistoricoRepository
import com.example.filmapp.home.historico.dataBase.HistoricoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoricoViewModel(app: Application) : AndroidViewModel(app) {

    val mediaList: LiveData<List<HistoricoEntity>>
    private val repository: HistoricoRepository

    init {
        val historicoDAO = FilmAppDataBase.getDataBase(app).historicoDao()
        repository = HistoricoRepository(historicoDAO)
        mediaList = repository.readAllData
    }

    fun saveNewItem(media: HistoricoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveInHistoricoTask(media)
        }
    }

    fun removeItem(media: HistoricoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOfHistoricoTask(media)
        }
    }

    fun formattingItem(list: List<HistoricoEntity>): List<HistoricoEntity>{

        list.forEach {
            var title = it.title
            var episodeTitle = it.episodeTitle
            var date = it.date

            //Formatação do Título da Série ou Filme
            if(title.length > 13){
                var newTitle = ""

                for (i in 0..12){

                    if (("${title?.get(12)}" == " ") && (i == 12)){
                        break
                    }

                    newTitle = newTitle + "${title?.get(i)}"
                }

                it.formattedTitle = newTitle + "..."
            }else{
                it.formattedTitle = title
            }

            //Formatação do Título do Episódio
            if(episodeTitle.length > 11){
                var newTitle = ""

                for (i in 0..10){

                    if (("${episodeTitle?.get(12)}" == " ") && (i == 12)){
                        break
                    }

                    newTitle = newTitle + "${episodeTitle?.get(i)}"
                }

                it.formattedEpisodeTitle = newTitle + "..."
            }else{
                it.formattedEpisodeTitle = episodeTitle
            }

            //Formatação da Data de Visualização
            if(date.length >= 2) {
                var year = "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
                var month = "${date?.get(5)}" + "${date?.get(6)}"
                var day = "${date?.get(8)}" + "${date?.get(9)}"
                var hour = "${date?.get(11)}" + "${date?.get(12)}"
                var minute = "${date?.get(14)}" + "${date?.get(15)}"

                it.date = day + "/" + month + "/" + year + " às " + hour + ":" + minute

            }
        }

        return list
    }
}