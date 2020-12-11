package com.example.filmapp.Series.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmapp.R
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



<<<<<<< HEAD
class EpisodioFragment(val img: String?, val sinopse: String?): Fragment() {
=======
class EpisodioFragment(val img: String?, val sinopse: String?, val imgLogo: String?, val homePage: String?): Fragment() {
>>>>>>> Marcus
    val scope = CoroutineScope(Dispatchers.Main)
    var selAssistirMaisTarde: Boolean = false
    var selFav: Boolean = false
    var selcomp: Boolean = false
    var selVisto: Boolean = false
    val picasso = Picasso.get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_series_episodio, container, false)


        picasso.load(img).into(view.imgEp)
        view.sinopseEp.text = sinopse
        picasso.load(imgLogo).into(view.imgLogo)
        view.imgTarde.setOnClickListener {
            AlteraIconAssistirMaisTarde()
        }
        view.imgVisto.setOnClickListener {
            AlteraIconAvaliar()
        }
        view.imgFav.setOnClickListener {
            AlteraIconFavorito()
        }
        view.imgCompart.setOnClickListener {
            AbrirCompartilhar()
            AlteraIconCompartilhar()
        }
        view.imgLogo.setOnClickListener {
            AbrirSiteLogo()
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

        fun AlteraIconAvaliar() {
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

        fun AbrirCompartilhar() {
            val ShareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "Compartilhe com amigos o que gostou!")
                this.type = "text/plain"
            }
            startActivity(ShareIntent)
        }

    }


