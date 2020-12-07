package com.example.filmapp.Media.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmapp.Entities.APIConfig.Config
import com.example.filmapp.R
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

class GeralMediaFragment(val poster: String?, val sinopse: String?): Fragment() {
    val scope = CoroutineScope(Dispatchers.Main)
    var selAssistirMaisTarde: Boolean = false
    var selFav: Boolean = false
    var selcomp: Boolean = false
    var selAcompanhar: Boolean = false
    var progr = 0
    val picasso = Picasso.get()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_media_geral, container, false)

        view.tv_sinopse.text = sinopse
      picasso.load(poster).into(view.img_geral)

        view.progress_circular.setOnClickListener{
            incrCircleBar()
        }

        view.imgTarde.setOnClickListener {
            AlteraIconAssistirMaisTarde()
        }
        view.imgAcompanhar.setOnClickListener {
            AlteraIconAcompanhar()
        }
        view.imgFav.setOnClickListener {
            AlteraIconFavorito()
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


}