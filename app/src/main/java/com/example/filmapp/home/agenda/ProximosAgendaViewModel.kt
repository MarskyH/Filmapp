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
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlin.math.log

class ProximosAgendaViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference

    var detaisSerie = MutableLiveData<TvDetails>()
    var listUser = MutableLiveData<ArrayList<ResultTv>>()
    var returnNovosEpisodiosListAPI = MutableLiveData<BaseTv>()
    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()

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
        listDataBase: ArrayList<AcompanhandoScope>
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

    fun getDetailsSerie(serie: ResultTv) {
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
            if (date.length == 10) {
                var year =
                    "${date?.get(0)}" + "${date?.get(1)}" + "${date?.get(2)}" + "${date?.get(3)}"
                var month = "${date?.get(5)}" + "${date?.get(6)}"
                var day = "${date?.get(8)}" + "${date?.get(9)}"

                detaisSerie.value!!.next_episode_to_air.air_date = day + "/" + month + "/" + year

            }

            //Formatação do Título da Série
            if (serieTitle.length > 13) {
                var newTitle = ""

                for (i in 0..12) {

                    if (("${serieTitle?.get(12)}" == " ") && (i == 12)) {
                        break
                    }

                    newTitle = newTitle + "${serieTitle?.get(i)}"
                }

                detaisSerie.value!!.formattedName = newTitle + "..."
            } else {
                detaisSerie.value!!.formattedName = serieTitle
            }

            //Formatação do Título do Episódio
            if (episodeTitle.length > 11) {
                var newTitle = ""

                for (i in 0..10) {

                    if (("${episodeTitle?.get(12)}" == " ") && (i == 12)) {
                        break
                    }

                    newTitle = newTitle + "${episodeTitle?.get(i)}"
                }

                detaisSerie.value!!.next_episode_to_air.formattedNameEpisode = newTitle + "..."
            } else {
                detaisSerie.value!!.next_episode_to_air.formattedNameEpisode = episodeTitle
            }
        }
    }

    //Firebase Realtime Database--------------------------------------------------------------------

    //Esta functio retorna a ultima lista salva no Realtime Database
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
                Log.i("Return DB Error:", error.message + " in Novidades")
            }
        })
    }

}