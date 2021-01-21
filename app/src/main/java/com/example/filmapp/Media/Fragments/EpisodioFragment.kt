package com.example.filmapp.Series.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.Media.Fragments.GeralMediaFragment
import com.example.filmapp.Media.Models.FavoritosViewModel
import com.example.filmapp.R
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import com.example.filmapp.home.agenda.dataBase.AssistirMaisTardeEntity
import com.example.filmapp.home.historico.HistoricoViewModel
import com.example.filmapp.home.historico.dataBase.HistoricoEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_series_episodio.*
import kotlinx.android.synthetic.main.fragment_series_episodio.view.*
import kotlinx.android.synthetic.main.fragment_series_geral.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.imgTarde
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgCompart
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgFav
import kotlinx.android.synthetic.main.fragment_series_geral.view.imgTarde
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EpisodioFragment : Fragment() {

    private lateinit var viewModelVisto: HistoricoViewModel
    val scope = CoroutineScope(Dispatchers.Main)
    var selcomp: Boolean = false
    var selVisto: Boolean = false
    val picasso = Picasso.get()
    var Poster: String? = null
    var Sinopse: String? = null
    var Type: String? = null
    var Title: String? = null
    var Id: String? = null
    var Img: String? = null
    var Logo: String? = null
    var HomePage: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Poster = arguments?.getString(poster)
            Sinopse = arguments?.getString(sinopse)
            Id = arguments?.getString(idMedia)
            Type = arguments?.getString(type)
            Img = arguments?.getString(img)
            Logo = arguments?.getString(logo)
            HomePage = arguments?.getString(homePage)
            Title = arguments?.getString(title)
            Log.i("type", Type.toString())
        }
    }

    companion object {
        private val sinopse = "sinopse"
        private val poster = "poster"
        private val idMedia = "id"
        private val logo = "logo"
        private val homePage = "home"
        private val img = "img"
        private val type = "type"
        private val title = "title"
        fun newInstance(Sinopse: String?, Poster: String?, Id: String?, Type: String?, Img: String?, Logo: String?, HomePage:String, Title: String? ): EpisodioFragment {
            val fragment = EpisodioFragment()
            val args = Bundle()
            args.putString(sinopse, Sinopse)
            args.putString(poster, Poster)
            args.putString(idMedia, Id)
            args.putString(type, Type)
            args.putString(img, Img)
            args.putString(logo, Logo)
            args.putString(homePage, HomePage)
            args.putString(title, Title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_series_episodio, container, false)
        viewModelVisto = ViewModelProvider(this).get(HistoricoViewModel::class.java)


        picasso.load(Img).into(view.imgEp)
        view.sinopseEp.text = Sinopse
        picasso.load(Logo).into(view.imgLogo)
        Log.i("Teste historico", "${Id} ${Title} ${Poster} ${Type}")

        view.imgVisto.setOnClickListener {
            if (selVisto == false) {
                AlteraIconVisto()
                addHistoricoList(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                Log.i("Teste historico", "${Id} ${Title} ${Poster} ${Type}")
                Toast.makeText(activity, "$Title adicionado ao Histórico", Toast.LENGTH_SHORT).show()
            } else {
                AlteraIconVisto()
                removeHistoricoList(Id!!.toString().toInt(), Title!!, Poster!!, Type!!)
                Toast.makeText(activity, "$Title removido do Histórico", Toast.LENGTH_SHORT).show()
            }
        }

        view.imgCompart.setOnClickListener {
            AbrirCompartilhar(Title!!, homePage)
            AlteraIconCompartilhar()
        }
        view.imgLogo.setOnClickListener {
            AbrirSiteLogo()
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

    fun AlteraIconVisto() {
        if (selVisto == false) {
            imgVisto.setImageResource(R.drawable.ic_visto_roxo)
            selVisto = true
        } else if (selVisto == true) {
            imgVisto.setImageResource(R.drawable.ic_visto_branco)
            selVisto = false
        }
    }

    fun AbrirSiteLogo() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(homePage)
        )
        startActivity(intent)
    }

    fun AbrirCompartilhar(title: String, link: String) {
        val ShareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "Já viu $title? Tá disponível aqui:$link")
            this.type = "text/plain"
        }
        startActivity(ShareIntent)
    }

    fun addHistoricoList(id: Int, title: String, poster_path: String, type: String) {
        viewModelVisto.saveNewItem(HistoricoEntity(id, title, poster_path, type))
    }

    fun removeHistoricoList(id: Int, title: String, poster_path: String, type: String) {
        viewModelVisto.removeItem(HistoricoEntity(id, title, poster_path, type))
    }

}


