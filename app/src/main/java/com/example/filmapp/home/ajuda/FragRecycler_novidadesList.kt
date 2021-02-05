package com.example.filmapp.home.ajuda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.*
import kotlinx.android.synthetic.main.fragrecycler_novidadeslist.*
import kotlinx.android.synthetic.main.fragrecycler_novidadeslist.view.*

class FragRecycler_novidadesList : Fragment(), NovidadesAdapter.onNovidadeItemClickListener {

    private lateinit var novidadesListAdapter: NovidadesAdapter
    private lateinit var novidadesListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<NovidadesViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NovidadesViewModel(service) as T
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
        novidadesListAdapter = NovidadesAdapter(this)
        view.rc_novidades.layoutManager = novidadesListLayoutManager
        view.rc_novidades.adapter = novidadesListAdapter
        view.rc_novidades.isHorizontalFadingEdgeEnabled
        view.rc_novidades.setHasFixedSize(true)

        viewModel.returnNovidades.observe(viewLifecycleOwner){
            pb_novidadesList.setVisibility(View.GONE)
            novidadesListAdapter.addList(it)
        }

        viewModel.getNovidadesList()

        return view
    }

    companion object {
        fun newInstance() = FragRecycler_novidadesList()
    }

    override fun novidadeItemClick(position: Int) {
        viewModel.returnNovidades.observe(viewLifecycleOwner) {
            val novidade = it.get(position)

            //Abrindo os detalhes da dúvida
            var intent = Intent(context, AjudaDetailsActivity::class.java)
            intent.putExtra("title", novidade.title)
            intent.putExtra("body", novidade.body)

            startActivity(intent)
        }

    }

}