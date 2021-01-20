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
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.Media.Models.FavoritosViewModel
import com.example.filmapp.Media.dataBase.FavoritosEntity
import com.example.filmapp.R
import com.example.filmapp.Services.MainViewModel
import com.example.filmapp.Services.service
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_media_geral.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.*
import kotlinx.android.synthetic.main.fragment_series_geral.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgAcompanhar
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgTarde
import kotlinx.android.synthetic.main.fragment_series_geral.view.progress_circular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

class GeralMediaFragment() : Fragment() {
    private lateinit var viewModelFav: FavoritosViewModel
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
    var title: String = ""
    var Id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Poster = arguments?.getString(poster)
            Sinopse = arguments?.getString(sinopse)
            Id = arguments?.getString(idMedia)
            Log.i("id geral", Id.toString())
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFav = ViewModelProvider(this).get(FavoritosViewModel::class.java)

        if (type == "Movie") {
            viewModelDetails.listDetailsMovies.observe(viewLifecycleOwner) {
                title = it.title
                Log.i("title", it.title)
                Toast.makeText(activity, it.title.toString(), Toast.LENGTH_SHORT).show()
            }
            viewModelDetails.getMovieDetails(Id!!)
        }
        if (type == "Tv") {
            viewModelDetails.listDetailsSeries.observe(viewLifecycleOwner) {
                title = it.name
                Log.i("title", it.name)
                Toast.makeText(activity, it.name.toString(), Toast.LENGTH_SHORT).show()

            }
            viewModelDetails.getTvDetails(Id!!)
        }

        val view: View = inflater!!.inflate(R.layout.fragment_media_geral, container, false)

        view.tv_sinopse.text = Sinopse
        picasso.load(Poster).into(view.img_geral)

        view.progress_circular.setOnClickListener {
            incrCircleBar()
        }

        view.imgTarde.setOnClickListener {
            AlteraIconAssistirMaisTarde()
        }
        view.imgAcompanhar.setOnClickListener {
            AlteraIconAcompanhar()
        }
        view.imgFav.setOnClickListener {
            if (selFav == false) {
                AlteraIconFavorito()
                addFavoritosList(Id!!.toString().toInt(), title!!, Poster!!, Type!!)
                Toast.makeText(activity, "Filme adicionado aos Favoritos", Toast.LENGTH_SHORT).show()
            } else {
                AlteraIconFavorito()
                remogveFavoritosList(Id!!.toString().toInt(), title!!, Poster!!, Type!!)
                Toast.makeText(activity, "Filme removido dos Favoritos", Toast.LENGTH_SHORT).show()
            }


        }
        view.imgCompart.setOnClickListener {
            AlteraIconCompartilhar()
            AbrirCompartilhar()
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
            imgFav.setImageResource(R.drawable.ic_favorito_roxo)
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

    fun AbrirCompartilhar() {
        val ShareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Compartilhe com amigos o que gostou!")
            this.type = "text/plain"
        }
        startActivity(ShareIntent)
    }

    fun addFavoritosList(id: Int, title: String, poster_path: String, type: String) {
        viewModelFav.saveNewMedia(FavoritosEntity(id, title, poster_path, type))
    }

    fun remogveFavoritosList(id: Int, title: String, poster_path: String, type: String) {
        viewModelFav.removeMedia(FavoritosEntity(id, title, poster_path, type))
    }


}
