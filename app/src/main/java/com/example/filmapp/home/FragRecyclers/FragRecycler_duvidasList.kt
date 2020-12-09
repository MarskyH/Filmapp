package com.example.filmapp.Home.FragRecyclers

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Classes.Ajuda
import com.example.filmapp.Home.Adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.Home.Adapters.RecyclerViews.AssistirMaisTardeAdapter
import com.example.filmapp.Home.AjudaActivity
import com.example.filmapp.Home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.AssistirMaisTardeViewModel
import com.example.filmapp.home.FragRecyclers.viewmodels.DuvidasViewModel
import kotlinx.android.synthetic.main.activity_ajuda.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*

class FragRecycler_duvidasList : Fragment(), AjudaAdapter.onAjudaItemClickListener {

    private lateinit var duvidasListAdapter: AjudaAdapter
    private lateinit var duvidasListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<DuvidasViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DuvidasViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_duvidaslist, container, false)

        //Iniciando o ReciclerView Dúvidas
        duvidasListLayoutManager = LinearLayoutManager(context)
        duvidasListAdapter = AjudaAdapter(this)
        view.rc_duvidas.layoutManager = duvidasListLayoutManager
        view.rc_duvidas.adapter = duvidasListAdapter
        view.rc_duvidas.isHorizontalFadingEdgeEnabled
        view.rc_duvidas.setHasFixedSize(true)

        viewModel.returnDuvidas.observe(viewLifecycleOwner){
            duvidasListAdapter.addList(it)
        }

        viewModel.getDuvidasList()

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_duvidasList()
    }

    override fun ajudaItemClick(position: Int) {
        viewModel.returnDuvidas.observe(viewLifecycleOwner){
            var duvida = it.get(position)

            //Abrindo o fragment AjudaDetailsFragment
            (activity as AjudaActivity).supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_ajudaDetails, AjudaDetailsFragment.newInstance())
                commit()
            }
        }
    }
}