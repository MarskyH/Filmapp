package com.example.filmapp.home.acompanhando

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Services.Service
import com.example.filmapp.dataBase.AcompanhandoRepository
import com.example.filmapp.dataBase.AssistirMaisTardeRepository
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AcompanhandoViewModel(val service: Service) : ViewModel() {

    var mediaDetails = MutableLiveData<TvDetails>()
    var listUpdated = MutableLiveData<List<AcompanhandoEntity>>()

    fun getStatusSeries(list: List<AcompanhandoEntity>) {
        viewModelScope.launch {

            list.forEach {

                var details = service.getDetailsSerie(
                    it.id.toString(),
                    API_KEY,
                    LANGUAGE,
                    1
                )

                it.number_of_episodes = details.number_of_episodes
                it.number_of_seasons = details.number_of_seasons
                it.poster_path = details.poster_path
                it.title = details.name

//Lógica para acompanhar o progresso do usuário-----------------------------------------------------

                var nextEpisodeOfUserNumber = it.lastEpisode + 1
                var nextEpisodeOfUserTitle = ""
                var serieId = it.id
                var currentSeasonOfUser = service.getSesaonDetails(
                    serieId,
                    it.currentSeason,
                    API_KEY,
                    LANGUAGE
                )

                //Retorna o nome do episódio no qual o usuário está
                if (nextEpisodeOfUserNumber <= currentSeasonOfUser.episodes.size) {
                    currentSeasonOfUser.episodes.forEach {
                        if (it.episode_number == nextEpisodeOfUserNumber) {
                            nextEpisodeOfUserTitle = it.name
                        }
                    }

                    it.nextEpisodeTitle = nextEpisodeOfUserTitle
                    it.nextEpisodeNumber = nextEpisodeOfUserNumber
                    it.userProgress = ((it.totalEpisodesWatched.toDouble() / it.number_of_episodes.toDouble()) * 100.0).toInt()

                } else if ((it.currentSeason + 1) <= it.number_of_seasons) { //Significa que o usuário está em uma nova temporada
                    it.currentSeason = it.currentSeason + 1
                    nextEpisodeOfUserNumber = 1
                    currentSeasonOfUser = service.getSesaonDetails(
                        serieId,
                        it.currentSeason,
                        API_KEY,
                        LANGUAGE
                    )

                    currentSeasonOfUser.episodes.forEach {
                        if (it.episode_number == nextEpisodeOfUserNumber) {
                            nextEpisodeOfUserTitle = it.name
                        }
                    }

                    it.nextEpisodeTitle = nextEpisodeOfUserTitle
                    it.nextEpisodeNumber = nextEpisodeOfUserNumber
                    it.userProgress = ((it.totalEpisodesWatched.toDouble() / it.number_of_episodes.toDouble()) * 100.0).toInt()

                } else {
                    it.userProgress = 100
                    it.finished = true
                }

//--------------------------------------------------------------------------------------------------

            }

            listUpdated.value = formattingItems(list)
        }
    }

    fun formattingItems(list: List<AcompanhandoEntity>): List<AcompanhandoEntity> {
        list.forEach {

            //Formatação do Título da Série
            if (it.title.length > 15) {
                var newTitle = ""

                for (i in 0..14) {

                    if (("${it.title?.get(14)}" == " ") && (i == 14)) {
                        break
                    }

                    newTitle = newTitle + "${it.title?.get(i)}"
                }

                it.title = newTitle + "..."
            } else {
                it.title = it.title
            }

            //Formatação do Título do Episódio
            if (it.nextEpisodeTitle.length > 15) {
                var newTitle = ""

                for (i in 0..14) {

                    if (("${it.nextEpisodeTitle?.get(14)}" == " ") && (i == 14)) {
                        break
                    }

                    newTitle = newTitle + "${it.nextEpisodeTitle?.get(i)}"
                }

                it.nextEpisodeTitle = newTitle + "..."
            } else {
                it.nextEpisodeTitle = it.nextEpisodeTitle
            }
        }

        return list
    }

}

class AcompanhandoDataBaseViewModel(app: Application) : AndroidViewModel(app) {

    val mediaList: LiveData<List<AcompanhandoEntity>>
    private val repository: AcompanhandoRepository

    init {
        val acompanhandoDAO = FilmAppDataBase.getDataBase(app).acompanhandoDao()
        repository = AcompanhandoRepository(acompanhandoDAO)
        mediaList = repository.readAllData
    }

    fun saveNewItem(media: AcompanhandoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveInAcompanhandoListListTask(media)
        }
    }

    fun removeItem(media: AcompanhandoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeOfAcompanhandoListTask(media)
        }
    }

    fun checkSerieInList(
        listAPI: ArrayList<ResultTv>,
        listDataBase: List<AcompanhandoEntity>
    ): ArrayList<ResultTv> {
        var listResult = arrayListOf<ResultTv>()

        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id)
                    media.followingStatusIndication = true
            }

            listResult.add(media)
        }

        return listResult
    }

}