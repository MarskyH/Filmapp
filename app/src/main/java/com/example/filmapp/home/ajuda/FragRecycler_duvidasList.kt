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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.activity_acompanhando.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.*
import kotlinx.android.synthetic.main.fragrecycler_duvidaslist.view.*

class FragRecycler_duvidasList : Fragment(), DuvidasAdapter.onDuvidaItemClickListener {

    private lateinit var duvidasListAdapter: DuvidasAdapter
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
        duvidasListAdapter = DuvidasAdapter(this)
        view.rc_duvidas.layoutManager = duvidasListLayoutManager
        view.rc_duvidas.adapter = duvidasListAdapter
        view.rc_duvidas.isHorizontalFadingEdgeEnabled
        view.rc_duvidas.setHasFixedSize(true)

        viewModel.returnDuvidas.observe(viewLifecycleOwner){
            pb_duvidasList.setVisibility(View.GONE)
            duvidasListAdapter.addList(it)
        }

        viewModel.getDuvidasList()

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_duvidasList()
    }

    override fun duvidaItemClick(position: Int) {
        viewModel.returnDuvidas.observe(viewLifecycleOwner){
            var duvida = it.get(position)

            //Abrindo os detalhes da dúvida
            var intent = Intent(context, AjudaDetailsActivity::class.java)
            intent.putExtra("title", duvida.title)
            intent.putExtra("body", duvida.body)

            startActivity(intent)
        }
    }
}