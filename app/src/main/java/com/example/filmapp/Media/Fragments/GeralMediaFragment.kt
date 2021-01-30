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
import com.example.filmapp.Media.Models.FavoritosViewModel
import com.example.filmapp.Media.dataBase.FavoritosDAO
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import com.example.filmapp.dataBase.FilmAppDataBase
import com.example.filmapp.home.acompanhando.AcompanhandoDataBaseViewModel
import com.example.filmapp.home.acompanhando.dataBase.AcompanhandoEntity
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_media_geral.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.*
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgAcompanhar
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgTarde
import kotlinx.android.synthetic.main.fragment_series_geral.view.progress_circular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.IDN

class GeralMediaFragment() : Fragment() {
    private lateinit var viewModelFav: FavoritosViewModel
    private lateinit var viewModelAcom: AcompanhandoDataBaseViewModel
    private lateinit var viewModelTarde: AssistirMaisTardeViewModel
    private val scope = CoroutineScope(Dispatchers.Main)
    private var selAssistirMaisTarde: Boolean = false
    private var selFav: Boolean = false
    private var selcomp: Boolean = false
    private var selAcompanhar: Boolean = false
    private var progr = 0.0
    private val picasso = Picasso.get()
    private var Poster: String? = null
    private var Sinopse: String? = null
    private var Type: String? = null
    private var Title: String = ""
    private var Id: String? = null
    private lateinit var posterBd: String
    private var contEp = 1
    private var contFilm = 1
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

        viewModelFav = ViewModelProvider(this).get(FavoritosViewModel::class.java)
        viewModelAcom = ViewModelProvider(this).get(AcompanhandoDataBaseViewModel::class.java)
        viewModelTarde = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        if (Type == "Movie") {
            viewModelDetails.listDetailsMovies.observe(viewLifecycleOwner) {
                Title = it.title
                rateFilm = it.vote_average
                posterBd = it.poster_path.toString()
                progr = rateFilm * 10
                media = FavoritosEntity(Id!!.toString().toInt(), Title!!, posterBd!!, Type!!)
                //checkList(media)
                updateProgressBar()
                Toast.makeText(activity, it.title, Toast.LENGTH_SHORT).show()
            }
            viewModelDetails.getMovieDetails(Id!!)
        }
        if (Type == "Tv") {
            viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner) {
                Title = it.name
                posterBd = it.poster_path
                numberEP = it.number_of_episodes
                rateSerie = it.vote_average
                progr = rateSerie * 10
                media = FavoritosEntity(Id!!.toString().toInt(), Title!!, posterBd!!, Type!!)
                //checkList(media)
                updateProgressBar()
                Toast.makeText(activity, it.name, Toast.LENGTH_SHORT).show()
            }
            viewModelDetails.getTvDetails(Id!!)
        }


        val view: View = inflater!!.inflate(R.layout.fragment_media_geral, container, false)

        if (Sinopse != "" && Sinopse != null) {
            view.tv_sinopse.text = Sinopse
        } else {
            view.tv_sinopse.text = "Sem sinopse disponível no momento"
        }

        picasso.load(URL_IMAGE + Poster).into(view.img_geral)


        view.imgTarde.setOnClickListener {
            if (selAssistirMaisTarde == false) {
                AlteraIconAssistirMaisTarde()
                addMaisTardeList(Id!!.toString().toInt(), Title!!, posterBd!!, Type!!)
                Toast.makeText(activity, "Assistir Mais Tarde: $Title", Toast.LENGTH_SHORT).show()
            } else {
                AlteraIconAssistirMaisTarde()
                removeMaisTardeList(Id!!.toString().toInt(), Title!!, posterBd!!, Type!!)
                Toast.makeText(activity, "Assistir Mais Tarde: $Title", Toast.LENGTH_SHORT).show()
            }
        }
        view.imgAcompanhar.setOnClickListener {
            AlteraIconAcompanhar()
            viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner) {
                var media = it
                var newItem = AcompanhandoEntity(media.id, media.name, media.poster_path)
                viewModelAcom.saveNewItem(newItem)
                Toast.makeText(context, "Acompanhando: ${media.name}", Toast.LENGTH_SHORT).show()
            }
        }

        view.imgFav.setOnClickListener {
            if (selFav == false) {
                AlteraIconFavorito()
                addFavoritosList(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                Toast.makeText(activity, "${Title}  adicionado aos Favoritos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                media = FavoritosEntity(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                //checkList(media)
                AlteraIconFavorito()
                removeFavoritosList(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                Toast.makeText(activity, "${Title} removido dos Favoritos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        view.imgCompart.setOnClickListener {
            AlteraIconCompartilhar()
            AbrirCompartilhar(Title!!, Poster!!)
        }

        return view

    }

    fun AlteraIconAssistirMaisTarde() {
        if (selAssistirMaisTarde == false) {
            imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde_roxo)
            selAssistirMaisTarde = true
        } else if (selAssistirMaisTarde == true) {
            imgTarde.setImageResource(R.drawable.ic_assistir_mais_tarde)
            selAssistirMaisTarde = false
        }

    }

    fun AlteraIconFavorito() {
        if (selFav == false) {
            imgFav.setImageResource(R.drawable.ic_favorito_select)
            selFav = true
        } else if (selFav == true) {
            imgFav.setImageResource(R.drawable.ic_favorito_branco)
            selFav = false
        }

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

    fun AlteraIconAcompanhar() {
        if (selAcompanhar == false) {
            imgAcompanhar.setImageResource(R.drawable.ic_acompanhando)
            selAcompanhar = true
        } else if (selAcompanhar == true) {
            imgAcompanhar.setImageResource(R.drawable.ic_acompanhando_roxo)
            selAcompanhar = false
        }
    }

    fun incrCircleBar(total: Int) {

        if (contEp < total) {
            var percent = getPercentEP(total)
            contEp += 1
            progr += percent
            updateProgressBar()
        } else {
            if (contEp == total || contEp > total) {
                progr = 100.0
                updateProgressBar()
            }
        }
    }

    fun updateProgressBar() {
        progress_circular.progress = progr.toInt()
        tvProgress.text = "$progr%"
    }


    fun getPercentEP(total: Int): Double {
        val percent = (100 / total).toDouble()
        return percent
    }


    fun AbrirCompartilhar(title: String, poster_path: String) {
        val ShareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Já viu $title? olha que pôster lindo:$poster_path")
            this.type = "text/plain"
        }
        startActivity(ShareIntent)
    }

    fun addFavoritosList(Id: Int, Title: String, Poster: String, Type: String) {
        viewModelFav.saveNewMedia(FavoritosEntity(Id, Title, Poster, Type))
    }

    fun removeFavoritosList(Id: Int, Title: String, Poster: String, Type: String) {
        viewModelFav.removeMedia(FavoritosEntity(Id, Title, Poster, Type))
    }

    fun addAcompanhandoList(id: Int, title: String, poster_path: String) {
        viewModelAcom.saveNewItem(AcompanhandoEntity(id, title, poster_path))
    }

    fun removeAcompanhandoList(id: Int, title: String, poster_path: String) {
        viewModelAcom.removeItem(AcompanhandoEntity(id, title, poster_path))
    }

    fun addMaisTardeList(id: Int, title: String, poster_path: String, type: String) {
        viewModelTarde.saveNewMedia(AssistirMaisTardeEntity(id, title, poster_path, type))
    }

    fun removeMaisTardeList(id: Int, title: String, poster_path: String, type: String) {
        viewModelTarde.removeMedia(AssistirMaisTardeEntity(id, title, poster_path, type))
    }

    fun checkList(media: FavoritosEntity) {
        var listFav: List<FavoritosEntity>
        var cont = 0
        viewModelFav.mediaListSerie.observe(viewLifecycleOwner) {
            listFav = it
            listFav.forEach {
                if (listFav[cont].id == media.id) {
                    selFav = false
                    AlteraIconFavorito()
                }
                cont += 1
            }

        }
        viewModelFav.mediaListMovie.observe(viewLifecycleOwner) {
            listFav = it
            listFav.forEach {
                if (listFav[cont].id == media.id) {
                    selFav = false
                    AlteraIconFavorito()
                }
                cont += 1
            }
        }
        cont = 0
    }

}
