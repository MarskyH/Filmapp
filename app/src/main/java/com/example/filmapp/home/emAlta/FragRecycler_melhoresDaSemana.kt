package com.example.filmapp.home.emAlta

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
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.fragrecycler_melhoresdasemana.view.*

class FragRecycler_melhoresDaSemana : Fragment(),
    MelhoresDaSemanaAdapter.onMelhoresDaSemanaItemClickListener {

    private lateinit var mediaListAdapter: MelhoresDaSemanaAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager

    val viewModel by viewModels<MelhoresDaSemanaViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresDaSemanaViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragrecycler_melhoresdasemana, container, false)

        //Iniciando o ReciclerView Melhores da Semana
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = MelhoresDaSemanaAdapter(this)
        view.rv_melhoresDaSemana_EmAlta.layoutManager = mediaListLayoutManager
        view.rv_melhoresDaSemana_EmAlta.adapter = mediaListAdapter
        view.rv_melhoresDaSemana_EmAlta.isHorizontalFadingEdgeEnabled
        view.rv_melhoresDaSemana_EmAlta.setHasFixedSize(true)

        viewModel.returnMelhoresDaSemanaListAPI.observe(viewLifecycleOwner){
            mediaListAdapter.addList(it)
        }

        viewModel.getMelhoresDaSemanaList()

        return view
    }

    companion object{
        fun newInstance() = FragRecycler_melhoresDaSemana()
    }

    override fun melhoresDaSemanaItemClick(position: Int) {
        viewModel.returnMelhoresDaSemanaListAPI.observe(viewLifecycleOwner) {
            var mediaList = it
            var media = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            startActivity(intent)
        }
    }

}