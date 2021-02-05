package com.example.filmapp.Media.Models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.dataBase.WatchedEpisodeScope
import com.example.filmapp.Services.Service
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EpisodioFragmentViewModel(val service: Service) : ViewModel() {

    //Firebase Auth
    val user = FirebaseAuth.getInstance().currentUser

    //Realtime Database
    var USER_ID = user!!.uid
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference

    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()
    var returnWatchedEpisodesList = MutableLiveData<ArrayList<WatchedEpisodeScope>>()

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
                Log.i("Return DB Error:", error.message + " in EpisodioPage")
            }
        })
    }

    //Esta function verifica se o usuário está acompanhando a série cujo o id é recebido
    fun checkSerieInList(
        id: Int,
        listDataBase: ArrayList<AcompanhandoScope>
    ): Boolean {

        listDataBase?.forEach {
            if (id == it.id)
                return true
        }

        return false
    }

    //Está function retorna a lista de episódios assistidos pelo usuário
    fun getWatchedEpisodesList(serieId: Int) {
        var watchedEpisodesList = arrayListOf<WatchedEpisodeScope>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if (it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "acompanhando") {
                                        it.children.forEach {
                                            if(it.key == serieId.toString()) {
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

                returnWatchedEpisodesList.value = watchedEpisodesList
                watchedEpisodesList = arrayListOf()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in EpisodioPage")
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

    //Está function adiciona o episódio a lista de episódios assistidos da respectiva série
    fun addWatchList(episodeId: Int, serieId: Int) {
        var newWatchedEpisode = WatchedEpisodeScope(episodeId)

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(serieId.toString())
            .child("watchedEpisodes")
            .child(episodeId.toString())
            .setValue(newWatchedEpisode)
    }

    //Está function exclui o episódio da lista de episódios assistidos da respectiva série
    fun deleteWatchList(episodeId: Int, serieId: Int) {

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(serieId.toString())
            .child("watchedEpisodes")
            .child(episodeId.toString())
            .removeValue()
    }
}