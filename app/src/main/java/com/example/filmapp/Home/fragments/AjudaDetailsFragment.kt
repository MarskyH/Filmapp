package com.example.filmapp.Home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmapp.Configuracaoes.ConfiguracoesActivity
import com.example.filmapp.Home.AjudaActivity
import com.example.filmapp.Home.DescubraActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragment_ajuda_details.*
import kotlinx.android.synthetic.main.fragment_ajuda_details.view.*

class AjudaDetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_ajuda_details, container, false)

        return view
    }

    companion object {
        fun newInstance() = AjudaDetailsFragment()
    }
}