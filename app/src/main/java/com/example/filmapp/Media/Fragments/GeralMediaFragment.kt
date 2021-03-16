package com.example.filmapp.Media.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.Entities.APIConfig.URL_IMAGE
import com.example.filmapp.Entities.Movie.MovieDetails
import com.example.filmapp.Entities.TV.TvDetails
import com.example.filmapp.Media.dataBase.FavoritoScope
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_media_geral.*
import kotlinx.android.synthetic.main.fragment_media_geral.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.imgAcompanhar
import kotlinx.android.synthetic.main.fragment_series_geral.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.imgTarde
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgAcompanhar
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgTarde
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GeralMediaFragment() : Fragment() {

    //Realtime Database
    private var cloudDatabase = FirebaseDatabase.getInstance()
    private var reference = cloudDatabase.reference
    private var serieChecked: TvDetails = TvDetails()
    private var serieResult: TvDetails = TvDetails()
    private var movieChecked: MovieDetails = MovieDetails()
    private var movieResult: MovieDetails = MovieDetails()

    private var mediaFavoritScope: FavoritoScope = FavoritoScope()
    private lateinit var mediaCheckedFavorito: FavoritoScope
    private lateinit var viewModelTarde: AssistirMaisTardeViewModel
    private val scope = CoroutineScope(Dispatchers.Main)
    private var selAssistirMaisTarde: Boolean = false
    private var selFav: Boolean = false
    private var selcomp: Boolean = false
    private var progr = 0.0
    private val picasso = Picasso.get()
    private var Poster: String? = null
    private var Sinopse: String? = null
    private var Type: String? = null
    private var Title: String = ""
    private var Id: String? = null
    private lateinit var posterBd: String
    private var rateFilm = 0.0
    private var rateSerie = 0.0
    private var numberEP = 0
    private lateinit var media: FavoritosEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Poster = arguments?.getString(poster)
            Sinopse = arguments?.getString(sinopse)
            Id = arguments?.getString(idMedia)
            Type = arguments?.getString(type)
        }
    }

    companion object {
        private val sinopse = "sinopse"
        private val poster = "poster"
        private val idMedia = "id"
        private val type = "type"
        fun newInstance(
            Sinopse: String?,
            Poster: String?,
            Id: String?,
            Type: String?
        ): GeralMediaFragment {
            val fragment = GeralMediaFragment()
            val args = Bundle()
            args.putString(sinopse, Sinopse)
            args.putString(poster, Poster)
            args.putString(idMedia, Id)
            args.putString(type, Type)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModelDetails by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(service) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelTarde = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        if (Type == "Movie") {
            viewModelDetails.listDetailsMovies.observe(viewLifecycleOwner) {
                mediaFavoritScope =
                    FavoritoScope(it.id, it.title, it.poster_path.toString(), Type.toString())

                movieResult = it

                //Personalização da IU
                if (it.title != null)
                    tv_mediaName_geralFrag.text = it.title
                else
                    tv_mediaName_geralFrag.text = it.original_title

                if (it.vote_average != null)
                    tv_evaluation_geralFrag.text =
                        (viewModelDetails.formatEvaluation(it.vote_average).toString() + "/5")
                else
                    tv_evaluation_geralFrag.text = "0.0/5"

                if (it.vote_average != null)
                    ratingBar_geralFrag.rating =
                        viewModelDetails.returnNumberOfStars(viewModelDetails.formatEvaluation(it.vote_average))
                            .toFloat()

                tv_mediaFirstAirDate_geralFrag.text = viewModelDetails.formatFirstAirDate(it.release_date)

                if (it.overview != null) {
                    tv_sinopse.text = it.overview
                } else {
                    tv_sinopse.text = "Não há sinopse disponível no momento."
                }

                viewModelDetails.getFavoritoist()
                viewModelDetails.getHistoricoInCloud()
                viewModelDetails.getAssistirMaisTardeListInCloud()
            }

            viewModelDetails.getMovieDetails(Id!!)

            viewModelDetails.returnHistoricoList.observe(viewLifecycleOwner) {
                movieChecked = viewModelDetails.checkMovieInHistorico(movieResult, it)
                Title = movieChecked.title
                posterBd = movieChecked.poster_path.toString()
                rateFilm = movieChecked.vote_average
                progr = rateFilm * 10

                if (movieChecked.watched == true) {
                    imgAcompanhar.setImageResource(R.drawable.ic_check_box_roxo)
                } else if (movieChecked.watched == false) {
                    imgAcompanhar.setImageResource(R.drawable.ic_check_box)
                }
            }

            viewModelDetails.returnAssistirMaisTardeList.observe(viewLifecycleOwner) {
                movieChecked = viewModelDetails.checkMovieInAssistirMaisTardeList(movieChecked, it)

                if (movieChecked.assistirMaisTardeIndication == true) {
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                } else if (movieChecked.assistirMaisTardeIndication == false) {
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde)
                }
            }

            viewModelDetails.returnFavoritoListMovie.observe(viewLifecycleOwner) {
                mediaCheckedFavorito = viewModelDetails.checkFavoritoInList(mediaFavoritScope, it)
                if (mediaCheckedFavorito.favoritoIndication == true) {
                    imgFav.setImageResource(R.drawable.ic_favorito_select)
                } else if (mediaCheckedFavorito.favoritoIndication == false) {
                    imgFav.setImageResource(R.drawable.ic_favorito_branco)
                }
            }
        }


        if (Type == "Tv") {
            viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner) {
                mediaFavoritScope = FavoritoScope(it.id, it.name, it.poster_path, Type.toString())

                serieResult = it

                //Personalização da IU
                if (it.name != null)
                    tv_mediaName_geralFrag.text = viewModelDetails.formatTitle(it.name)
                else
                    tv_mediaName_geralFrag.text = viewModelDetails.formatTitle(it.original_name)

                if (it.vote_average != null)
                    tv_evaluation_geralFrag.text =
                        (viewModelDetails.formatEvaluation(it.vote_average).toString() + "/5")
                else {
                    tv_evaluation_geralFrag.visibility = View.GONE
                    ratingBar_geralFrag.visibility = View.GONE
                }

                if (it.vote_average != null)
                    ratingBar_geralFrag.rating =
                        viewModelDetails.returnNumberOfStars(viewModelDetails.formatEvaluation(it.vote_average))
                            .toFloat()

                tv_mediaFirstAirDate_geralFrag.text = viewModelDetails.formatFirstAirDate(it.first_air_date)

                if (it.overview != null) {
                    tv_sinopse.text = it.overview
                } else {
                    tv_sinopse.text = "Não há sinopse disponível no momento."
                }

                viewModelDetails.getAcompanhadoList()
                viewModelDetails.getFavoritoist()
                viewModelDetails.getAssistirMaisTardeListInCloud()
            }
            viewModelDetails.getTvDetails(Id!!)

            viewModelDetails.returnAcompanhandoList.observe(viewLifecycleOwner) {
                serieChecked = viewModelDetails.checkSerieInAcompanhandoList(serieResult, it)
                Title = serieChecked.name
                posterBd = serieChecked.poster_path
                numberEP = serieChecked.number_of_episodes
                rateSerie = serieChecked.vote_average
                progr = rateSerie * 10

                if (serieChecked.followingStatusIndication == true) {
                    if (serieChecked.finished == 1) {
                        imgAcompanhar.setImageResource(R.drawable.ic_check_box_roxo)
                    } else {
                        imgAcompanhar.setImageResource(R.drawable.ic_acompanhando_roxo)
                    }
                } else if (serieChecked.followingStatusIndication == false) {
                    imgAcompanhar.setImageResource(R.drawable.ic_acompanhando)
                }
            }

            viewModelDetails.returnAssistirMaisTardeList.observe(viewLifecycleOwner) {
                serieChecked = viewModelDetails.checkSerieInAssistirMaisTardeList(serieChecked, it)

                if (serieChecked.assistirMaisTardeIndication == true) {
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                } else if (serieChecked.assistirMaisTardeIndication == false) {
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde)
                }
            }

            viewModelDetails.returnFavoritoListSerie.observe(viewLifecycleOwner) {
                mediaCheckedFavorito = viewModelDetails.checkFavoritoInList(mediaFavoritScope, it)
                if (mediaCheckedFavorito.favoritoIndication == true) {
                    imgFav.setImageResource(R.drawable.ic_favorito_select)
                } else if (mediaCheckedFavorito.favoritoIndication == false) {
                    imgFav.setImageResource(R.drawable.ic_favorito_branco)
                }
            }
        }

        val view: View = inflater!!.inflate(R.layout.fragment_media_geral, container, false)

        if((Poster != null) && (Poster!!.isNotEmpty()))
            picasso.load(URL_IMAGE + Poster).into(view.img_geral)

        view.imgTarde.setOnClickListener {
            if (Type == "Tv") {
                if (serieChecked.assistirMaisTardeIndication == true) {
                    viewModelDetails.deleteFromAssistirMaisTardeList(serieResult.id)
                    serieChecked.assistirMaisTardeIndication = false
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde)
                    Toast.makeText(
                        context,
                        "Série removida da Agenda",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (serieChecked.assistirMaisTardeIndication == false) {
                    viewModelDetails.saveSerieInAssistirMaisTardeList(serieResult)
                    serieChecked.assistirMaisTardeIndication = true
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                    Toast.makeText(context, "Série adicionada a Agenda", Toast.LENGTH_SHORT).show()
                }
            } else if (Type == "Movie") {
                if (movieChecked.assistirMaisTardeIndication == true) {
                    viewModelDetails.deleteFromAssistirMaisTardeList(movieResult.id)
                    movieChecked.assistirMaisTardeIndication = false
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde)
                    Toast.makeText(
                        context,
                        "Filme removido da Agenda",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (movieChecked.assistirMaisTardeIndication == false) {
                    viewModelDetails.saveMovieInAssistirMaisTardeList(movieResult)
                    movieChecked.assistirMaisTardeIndication = true
                    imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
                    Toast.makeText(context, "Filme adicionado a Agenda", Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.imgAcompanhar.setOnClickListener {
            if (Type == "Tv") {
                if (serieChecked.followingStatusIndication == true) {
                    viewModelDetails.deleteFromAcompanhandoList(serieResult)
                    serieChecked.followingStatusIndication = false
                    imgAcompanhar.setImageResource(R.drawable.ic_acompanhando)
                    Toast.makeText(
                        context,
                        "Não está mais acompanhando: ${Title}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (serieChecked.followingStatusIndication == false) {
                    viewModelDetails.saveInAcompanhandoList(serieResult)
                    serieChecked.followingStatusIndication = true
                    imgAcompanhar.setImageResource(R.drawable.ic_acompanhando_roxo)
                    Toast.makeText(context, "Acompanhando: ${Title}", Toast.LENGTH_SHORT).show()
                }
            } else if (Type == "Movie") {
                if (movieChecked.watched == true) {
                    viewModelDetails.deleteFromHistoricoList(movieResult)
                    movieChecked.watched = false
                    imgAcompanhar.setImageResource(R.drawable.ic_check_box)
                    Toast.makeText(
                        context,
                        "${Title} foi removido do Histórico",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (movieChecked.watched == false) {
                    viewModelDetails.saveInHistoricoList(movieResult)
                    movieChecked.watched = true
                    imgAcompanhar.setImageResource(R.drawable.ic_check_box_roxo)
                    Toast.makeText(
                        context,
                        "${Title} foi adicionado ao Histórico",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        view.imgFav.setOnClickListener {
            if (mediaCheckedFavorito.favoritoIndication == true) {
                viewModelDetails.deleteFromFavoritoList(mediaFavoritScope)
                imgFav.setImageResource(R.drawable.ic_favorito_branco)
                mediaCheckedFavorito.favoritoIndication = false
                Toast.makeText(activity, "${Title} removido dos Favoritos", Toast.LENGTH_SHORT)
                    .show()

            } else if (mediaCheckedFavorito.favoritoIndication == false) {
                viewModelDetails.saveInFavoritoList(mediaFavoritScope)
                mediaCheckedFavorito.favoritoIndication = true
                imgFav.setImageResource(R.drawable.ic_favorito_select)
                Toast.makeText(activity, "${Title}  adicionado aos Favoritos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        view.imgCompart.setOnClickListener {
            AlteraIconCompartilhar()
            AbrirCompartilhar(Title!!, Poster!!)
        }

        return view

    }

    fun AlteraIconCompartilhar() {
        if (selcomp == false) {
            imgCompart.setImageResource(R.drawable.ic_compartilhar_roxo)
            selcomp = true
            scope.launch {
                delay(2000)
                imgCompart.setImageResource(R.drawable.ic_compartilhar)
                selcomp = false
            }
        }
    }

    fun AbrirCompartilhar(title: String, poster_path: String) {
        val ShareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Já viu $title? olha que pôster lindo:$poster_path")
            this.type = "text/plain"
        }
        startActivity(ShareIntent)
    }

}
