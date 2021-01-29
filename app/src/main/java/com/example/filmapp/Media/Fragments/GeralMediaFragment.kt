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
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
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

class GeralMediaFragment() : Fragment() {
    private lateinit var viewModelFav: FavoritosViewModel
    private lateinit var viewModelAcom: AcompanhandoDataBaseViewModel
    private lateinit var viewModelTarde: AssistirMaisTardeViewModel
    val scope = CoroutineScope(Dispatchers.Main)
    var selAssistirMaisTarde: Boolean = false
    var selFav: Boolean = false
    var selcomp: Boolean = false
    var selAcompanhar: Boolean = false
    var progr = 0
    val picasso = Picasso.get()
    var Poster: String? = null
    var Sinopse: String? = null
    var Type: String? = null
    var Title: String = ""
    var Id: String? = null
    lateinit var posterBd: String

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
        fun newInstance(Sinopse: String?, Poster: String?, Id: String?, Type: String?): GeralMediaFragment {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModelFav = ViewModelProvider(this).get(FavoritosViewModel::class.java)
        viewModelAcom = ViewModelProvider(this).get(AcompanhandoDataBaseViewModel::class.java)
        viewModelTarde = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        if (Type == "Movie") {
            viewModelDetails.listDetailsMovies.observe(viewLifecycleOwner) {
                Title = it.title
                posterBd = it.poster_path.toString()
                Toast.makeText(activity, it.title.toString(), Toast.LENGTH_SHORT).show()
            }
            viewModelDetails.getMovieDetails(Id!!)
        }
        if (Type == "Tv") {
            viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner) {
                Title = it.name
                posterBd = it.poster_path
                Toast.makeText(activity, it.name.toString(), Toast.LENGTH_SHORT).show()
            }
            viewModelDetails.getTvDetails(Id!!)
        }
        val view: View = inflater!!.inflate(R.layout.fragment_media_geral, container, false)

        if(Sinopse != "" && Sinopse != null){
            view.tv_sinopse.text = Sinopse
        }else{
            view.tv_sinopse.text = "Sem sinopse disponível no momento"
        }

        picasso.load(URL_IMAGE + Poster).into(view.img_geral)

        view.progress_circular.setOnClickListener {
            incrCircleBar()
        }


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
                viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner){
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
                Toast.makeText(activity, "${Title}  adicionado aos Favoritos", Toast.LENGTH_SHORT).show()
            } else {
                AlteraIconFavorito()
                removeFavoritosList(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                Toast.makeText(activity, "${Title} removido dos Favoritos", Toast.LENGTH_SHORT).show()
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

    fun incrCircleBar() {

        if (progr <= 90) {
            progr += 10
            updateProgressBar()
        }

    }

    fun updateProgressBar() {
        progress_circular.progress = progr
        tvProgress.text = "$progr%"
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
        viewModelAcom.saveNewItem(AcompanhandoEntity(id,title,poster_path))
    }

    fun removeAcompanhandoList(id: Int, title: String, poster_path: String){
        viewModelAcom.removeItem(AcompanhandoEntity(id,title,poster_path))
    }

    fun addMaisTardeList(id: Int, title: String, poster_path: String, type: String) {
        viewModelTarde.saveNewMedia(AssistirMaisTardeEntity(id,title,poster_path, type))
    }

    fun removeMaisTardeList(id: Int, title: String, poster_path: String, type: String){
        viewModelTarde.removeMedia(AssistirMaisTardeEntity(id,title,poster_path, type))
    }

    fun checkInListFavoritos(media: FavoritosEntity){

    }

}
