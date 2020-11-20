package com.example.filmapp.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.filmapp.R
import com.example.filmapp.home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        view.button_acompanhando.setOnClickListener{
            callAcompanhandoPage()
        }

        view.button_emAlta.setOnClickListener {
            callEmAltaPage()
        }

        view.button_ajuda.setOnClickListener {
            callAjudaPage()
        }

        view.button_agenda.setOnClickListener {
            callAgendaPage()
        }

        view.button_historico.setOnClickListener {
            callHistoricoPage()
        }

        view.button_melhores.setOnClickListener {
            callMelhoresPage()
        }

        return view
    }

    fun callAcompanhandoPage(){
        val intent = Intent(context, AcompanhandoActivity::class.java)
        startActivity(intent)
    }

    fun callEmAltaPage(){
        val intent = Intent(context, AltaActivity::class.java)
        startActivity(intent)
    }

    fun callAjudaPage(){
        val intent = Intent(context, AjudaActivity::class.java)
        startActivity(intent)
    }

    fun callAgendaPage(){
        val intent = Intent(context, AgendaActivity::class.java)
        startActivity(intent)
    }

    fun callHistoricoPage(){
        val intent = Intent(context, HistoricoActivity::class.java)
        startActivity(intent)
    }

    fun callMelhoresPage(){
        val intent = Intent(context, MelhoresActivity::class.java)
        startActivity(intent)
    }

}