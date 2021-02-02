package com.example.filmapp.home.acompanhando

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Services.Service
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class AcompanhandoViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    var cloudDatabase = FirebaseDatabase.getInstance()
    var reference = cloudDatabase.reference

    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()
    var listUpdated = MutableLiveData<ArrayList<AcompanhandoScope>>()

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

    //Está function atualiza a lista retornada do Realtime Database
    fun getCurrentStatusSeries(list: ArrayList<AcompanhandoScope>) {
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
                    it.finished = 1
                }

//--------------------------------------------------------------------------------------------------

            }

            listUpdated.value = formattingItems(list)
        }
    }

    fun formattingItems(list: ArrayList<AcompanhandoScope>): ArrayList<AcompanhandoScope> {
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

//Como salvar um episódio como salvo:

//FirebaseDatabase.getInstance().reference
//.child(userId)
//.child("acompanhando")
//.child(media.id.toString())
//.child("watchedEpisodes")
//.push()
//.setValue(episodeId)