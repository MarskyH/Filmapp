package com.example.filmapp.Media.Models

import android.util.Log
import com.example.filmapp.Services.Service


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.SimilarMovies
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.SeasonDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.dataBase.WatchedEpisodeScope
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TemporadaFragmentViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference

    var listSeasonDetails = MutableLiveData<SeasonDetails>()
    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()
    var verifiedSeries = MutableLiveData<TvDetails>()
    var returnWatchedEpisodesList = MutableLiveData<SeasonDetails>()

    fun getSeasonDetails(serieId: Int, seasonNumber: Int, following: Boolean) {
        viewModelScope.launch {
             var seasonDetails: SeasonDetails = service.getSesaonDetails(
                serieId,
                seasonNumber,
                API_KEY,
                LANGUAGE,
            )

            seasonDetails.followingStatusIndication = following
            seasonDetails.serieId = serieId

            listSeasonDetails.value = seasonDetails
        }
    }

    //Esta function retorna a ultima lista salva no Realtime Database
    fun getAcompanhadoList() {
        var acompanhadoList = arrayListOf<AcompanhandoScope>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "acompanhando") {
                                        it.children.forEach {

                                            var media = AcompanhandoScope(
                                                it.child("id").value.toString().toInt(),
                                                it.child("title").value.toString(),
                                                it.child("poster_path").value.toString(),
                                                it.child("number_of_episodes").value.toString()
                                                    .toInt(),
                                                it.child("number_of_seasons").value.toString()
                                                    .toInt(),
                                                it.child("lastEpisode").value.toString().toInt(),
                                                it.child("nextEpisodeTitle").value.toString(),
                                                it.child("nextEpisodeNumber").value.toString()
                                                    .toInt(),
                                                it.child("totalEpisodesWatched").value.toString()
                                                    .toInt(),
                                                it.child("currentSeason").value.toString().toInt(),
                                                it.child("userProgress").value.toString().toInt(),
                                                it.child("finished").value.toString().toInt()
                                            )

                                            acompanhadoList.add(media)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                returnAcompanhandoList.value = acompanhadoList
                acompanhadoList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in EpisodesList")
            }
        })
    }

    //Esta function verifica se o usuário está acompanhando a série cujo o id é recebido
    fun checkSerieInAcompanhandoList(
        serie: TvDetails,
        listDataBase: ArrayList<AcompanhandoScope>
    ){

        listDataBase?.forEach {
            if (serie.id == it.id) {
                serie.followingStatusIndication = true
                verifiedSeries.value = serie
            }
        }

        serie.followingStatusIndication = false
        verifiedSeries.value = serie
    }

    //Está function retorna a lista de episódios assistidos pelo usuário
    fun checkWatchedEpisodesList(season: SeasonDetails) {
        var watchedEpisodesList = arrayListOf<WatchedEpisodeScope>()

        //Retorna os episódios da série que o usuário já assistiu
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "acompanhando") {
                                        it.children.forEach {
                                            if(it.key == season.serieId.toString()) {
                                                it.children.forEach {
                                                    if (it.key == "watchedEpisodes") {
                                                        it.children.forEach {

                                                            var watchedEpisode = WatchedEpisodeScope(
                                                                it.child("id").value.toString().toInt()
                                                            )

                                                            watchedEpisodesList.add(watchedEpisode)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                //Verificando quais episódios da série o usuário já assistiu
                watchedEpisodesList.forEach {
                    var episodeId = it.id

                    season.episodes.forEach {
                        if (episodeId == it.id)
                            it.watched = true
                    }

                }

                returnWatchedEpisodesList.value = season
                watchedEpisodesList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in EpisodesList")
            }
        })
    }

    //Esta function verifica se o usuário está acompanhando a série cujo o id é recebido
    fun checkIfWatchedEpisode(
        id: Int,
        listDataBase: ArrayList<WatchedEpisodeScope>
    ): Boolean {

        listDataBase?.forEach {
            if (id == it.id)
                return true
        }

        return false
    }

    //Formatação dos dados dos episódios
    fun formattingEpisodes(season: SeasonDetails): SeasonDetails {

        //Obtendo a data atual do dispositivo
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        var currentDay = ("${currentDate?.get(0)}" + "${currentDate?.get(1)}").toInt()
        var currentMonth = ("${currentDate?.get(3)}" + "${currentDate?.get(4)}").toInt()
        var currentYear =
            ("${currentDate?.get(6)}" + "${currentDate?.get(7)}" + "${currentDate?.get(8)}" + "${
                currentDate?.get(9)
            }").toInt()

        //Formatando episódio por episódio
        season.episodes.forEach {

            //Formatação do Título
            var title = it.name

            if (title.length > 27) {
                var newTitle = ""

                for (i in 0..23) {

                    if (("${title?.get(23)}" == " ") && (i == 23)) {
                        break
                    }

                    newTitle = newTitle + "${title?.get(i)}"
                }

                it.formattedTitle = newTitle + "..."
            } else {
                it.formattedTitle = title
            }

            //Tratando a Data de Lançamento do episódio
            if (it.air_date != "" && it.air_date != null) {
                var airDate = it.air_date
                var airDay = ("${airDate?.get(8)}" + "${airDate?.get(9)}").toInt()
                var airMonth = ("${airDate?.get(5)}" + "${airDate?.get(6)}").toInt()
                var airYear = ("${airDate?.get(0)}" + "${airDate?.get(1)}" + "${airDate?.get(2)}" + "${airDate?.get(3)}").toInt()

                //Verificando se o episódio já foi lançado
                if (airYear < currentYear) {
                    it.released = true
                } else if (airYear > currentYear) {
                    it.released = false
                } else {
                    if (airMonth < currentMonth) {
                        it.released = true
                    } else if (airMonth > currentMonth) {
                        it.released = false
                    } else {
                        if (airDay <= currentDay) {
                            it.released = true
                        } else if (airDay > currentDay) {
                            it.released = false
                        }
                    }
                }

                //Formatação da Data de Lançamento
                var year = "${airDate?.get(0)}" + "${airDate?.get(1)}" + "${airDate?.get(2)}" + "${airDate?.get(3)}"
                var month = "${airDate?.get(5)}" + "${airDate?.get(6)}"
                var day = "${airDate?.get(8)}" + "${airDate?.get(9)}"

                    when (month) {
                        "01" -> month = "Janeiro"
                        "02" -> month = "Fevereiro"
                        "03" -> month = "Março"
                        "04" -> month = "Abril"
                        "05" -> month = "Maio"
                        "06" -> month = "Junho"
                        "07" -> month = "Julho"
                        "08" -> month = "Agosto"
                        "09" -> month = "Setembro"
                        "10" -> month = "Outubro"
                        "11" -> month = "Novembro"
                        "12" -> month = "Dezembro"
                        else -> month = " "
                    }

                it.formattedReleaseDate = day + " de " + month + ", " + year

            }
        }

        return season
    }

}
