package com.example.filmapp.home.agenda

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.ResultMovie
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Services.Service
import com.example.filmapp.home.acompanhando.AcompanhandoDataBaseViewModel
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import kotlinx.coroutines.launch
import kotlin.math.log

class ProximosAgendaViewModel(val service: Service) : ViewModel() {

    var detaisSerie = MutableLiveData<TvDetails>()
    var listUser = MutableLiveData<ArrayList<ResultTv>>()
    var returnNovosEpisodiosListAPI = MutableLiveData<BaseTv>()

    fun getNovosEpisodiosList() {
        viewModelScope.launch {
            returnNovosEpisodiosListAPI.value = service.getOnTheAir(
                "4a6baee1eff7d3911f03f59b9b8f43eb",
                "pt-BR"
            )
        }
    }

    fun checkEpisodiosForUser(
        listAPI: ArrayList<ResultTv>,
        listDataBase: List<AcompanhandoEntity>
    ): ArrayList<ResultTv> {
        var listResult = arrayListOf<ResultTv>()

        //Verificando se alguma série que terá novo episodio está sendo acompanhada pelo usuário
        listAPI?.forEach {
            var media = it

            listDataBase?.forEach {

                if (media.id == it.id)
                    listResult.add(media)

            }
        }

        listUser.value = listResult
        return listResult
    }

    fun getDetailsSerie(serie: ResultTv){
        viewModelScope.launch {
            detaisSerie.value = service.getDetailsSerie(
                serie.id.toString(),
                API_KEY,
                LANGUAGE,
                1
            )

            var date = detaisSerie.value!!.next_episode_to_air.air_date
            var serieTitle = detaisSerie.value!!.name
            var episodeTitle = detaisSerie.value!!.next_episode_to_air.name


            //Formatação da Data de Lançamento
            if(date.length == 10) {
                var year = "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
                var month = "${date?.get(5)}" + "${date?.get(6)}"
                var day = "${date?.get(8)}" + "${date?.get(9)}"

                detaisSerie.value!!.next_episode_to_air.air_date = day + "/" + month + "/" + year

            }

            //Formatação do Título da Série
            if(serieTitle.length > 13){
                var newTitle = ""

                for (i in 0..12){

                    if (("${serieTitle?.get(12)}" == " ") && (i == 12)){
                        break
                    }

                    newTitle = newTitle + "${serieTitle?.get(i)}"
                }

                detaisSerie.value!!.formattedName = newTitle + "..."
            }else{
                detaisSerie.value!!.formattedName = serieTitle
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

                detaisSerie.value!!.next_episode_to_air.formattedNameEpisode = newTitle + "..."
            }else{
                detaisSerie.value!!.next_episode_to_air.formattedNameEpisode = episodeTitle
            }
        }
    }

}