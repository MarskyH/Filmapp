package com.example.filmapp.home.FragRecyclers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.home.adapters.RecyclerViews.AjudaAdapter
import com.example.filmapp.home.AjudaActivity
import com.example.filmapp.home.fragments.AjudaDetailsFragment
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.FragRecyclers.viewmodels.DuvidasViewModel
import kotlinx.android.synthetic.main.fragrecycler_novidadeslist.view.*

class FragRecycler_novidadesList : Fragment(), AjudaAdapter.onAjudaItemClickListener {

    private lateinit var novidadesListAdapter: AjudaAdapter
    private lateinit var novidadesListLayoutManager: RecyclerView.LayoutManager

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
        var view = inflater.inflate(R.layout.fragrecycler_novidadeslist, container, false)

        //Iniciando o ReciclerView Dúvidas Frequentes
        novidadesListLayoutManager = LinearLayoutManager(context)
        novidadesListAdapter = AjudaAdapter(this)
        view.rc_novidades.layoutManager = novidadesListLayoutManager
        view.rc_novidades.adapter = novidadesListAdapter
        view.rc_novidades.isHorizontalFadingEdgeEnabled
        view.rc_novidades.setHasFixedSize(true)

        viewModel.returnNovidades.observe(viewLifecycleOwner){
            novidadesListAdapter.addList(it)
        }

//        viewModel.getNovidadesList()

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_novidadesList()
    }

    override fun ajudaItemClick(position: Int) {
        viewModel.returnNovidades.observe(viewLifecycleOwner) {
            val novidade = it.get(position)

            //Abrindo o fragment AjudaDetailsFragment
            (activity as AjudaActivity).supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_ajudaDetails, AjudaDetailsFragment.newInstance())
                commit()
            }

        }

    }

}