package com.example.filmapp.Services


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapp.Entities.APIConfig.API_KEY
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Entities.APIConfig.LANGUAGE
import com.example.filmapp.Entities.Movie.BaseMovie
import com.example.filmapp.Entities.Movie.MovieDetails
import com.example.filmapp.Entities.TV.BaseTv
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.dataBase.FavoritoScope
import com.example.filmapp.home.acompanhando.realtimeDatabase.AcompanhandoScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.coroutines.launch

class MainViewModel(val service: Service) : ViewModel() {
    val user = FirebaseAuth.getInstance().currentUser


    //Realtime Database
    var USER_ID = user!!.uid
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference

    var listResMovies = MutableLiveData<BaseMovie>()
    var listResSeries = MutableLiveData<BaseTv>()
    var listDetailsSeries = MutableLiveData<TvDetails>()
    var listDetailsMovies = MutableLiveData<MovieDetails>()
    var config = MutableLiveData<Config>()
    var returnAcompanhandoList = MutableLiveData<ArrayList<AcompanhandoScope>>()
    var returnFavoritoList = MutableLiveData<ArrayList<FavoritoScope>>()

//Realtime Database---------------------------------------------------------------------------------

    fun saveInAcompanhandoList(media: TvDetails) {
        var serie =
            AcompanhandoScope(id = media.id, title = media.name, poster_path = media.poster_path)

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(media.id.toString())
            .setValue(serie)
    }


    fun deleteFromAcompanhandoList(media: TvDetails){
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("acompanhando")
            .child(media.id.toString())
            .removeValue()
    }

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

    fun checkSerieInList(
        media: TvDetails,
        listDataBase: ArrayList<AcompanhandoScope>
    ): TvDetails {

            listDataBase?.forEach {

                if (media.id == it.id)
                    media.followingStatusIndication = true
            }

        return media
    }

    fun saveInFavoritoList(media: FavoritoScope) {
        var serie = FavoritoScope(id = media.id, title = media.title, poster_path = media.poster_path, type =  media.type)
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("favoritos")
            .child(media.id.toString())
            .setValue(serie)
    }

    fun deleteFromFavoritoList(media: FavoritoScope){
        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(USER_ID)
            .child("favoritos")
            .child(media.id.toString())
            .removeValue()
    }

    fun getFavoritoist() {
        var favoritoList = arrayListOf<FavoritoScope>()

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {

                    if(it.key == "users") {
                        it.children.forEach {
                            if (it.key == USER_ID) {
                                it.children.forEach {
                                    if (it.key == "favoritos") {
                                        it.children.forEach {
                                            var media = FavoritoScope(
                                                it.child("id").value.toString().toInt(),
                                                it.child("title").value.toString(),
                                                it.child("poster_path").value.toString(),
                                                it.child("type").value.toString()
                                            )

                                            favoritoList.add(media)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                returnFavoritoList.value = favoritoList
                favoritoList = arrayListOf()

            }override fun onCancelled(error: DatabaseError) {
                Log.i("Return DB Error:", error.message + " in Novidades")
            }
        })
    }

    fun checkFavoritoInList(
        media: FavoritoScope,
        listDataBase: ArrayList<FavoritoScope>
    ): FavoritoScope {

        listDataBase?.forEach {

            if (media.id == it.id)
                media.favoritoIndication = true
        }

        return media
    }

//--------------------------------------------------------------------------------------------------

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            listResMovies.value = service.getPopularMovies(
                API_KEY,
                LANGUAGE,
                page
            )

        }
    }

    fun getPopularSeries(page: Int) {
        viewModelScope.launch {
            listResSeries.value = service.getPopularSeries(
                API_KEY,
                LANGUAGE,
                page
            )

        }
    }

    fun getConfig() {
        viewModelScope.launch {
            config.value = service.getApiConfig(
                API_KEY,
            )
        }
    }

    fun getTvDetails(id: String) {
        viewModelScope.launch {
            listDetailsSeries.value = service.getDetailsSerie(
                id,
                API_KEY,
                LANGUAGE,
                1
            )

        }
    }

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            listDetailsMovies.value = service.getDetailsMovie(
                id,
                API_KEY,
                LANGUAGE,
                1
            )

        }
    }


}

