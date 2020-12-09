package com.example.filmapp.home.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.R
import com.example.filmapp.home.*
import com.example.filmapp.Services.service
import com.example.filmapp.home.fragments.viewmodels.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    val viewModel by viewModels<HomeFragmentViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeFragmentViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        view.button_acompanhando.setOnClickListener{
            val intent = Intent(context, AcompanhandoActivity::class.java)
            startActivity(intent)
        }

        view.button_emAlta.setOnClickListener {
            val intent = Intent(context, AltaActivity::class.java)
            startActivity(intent)
        }

        view.button_ajuda.setOnClickListener {
            val intent = Intent(context, AjudaActivity::class.java)
            startActivity(intent)
        }

        view.button_agenda.setOnClickListener {
            val intent = Intent(context, AgendaActivity::class.java)
            startActivity(intent)
        }

        view.button_historico.setOnClickListener {
            val intent = Intent(context, HistoricoActivity::class.java)
            startActivity(intent)
        }

        view.button_melhores.setOnClickListener {
            val intent = Intent(context, MelhoresActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}