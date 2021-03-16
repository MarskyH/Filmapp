package com.example.filmapp.home.melhores

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.Entities.TV.ResultTv
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.agenda.AssistirMaisTardeViewModel
import kotlinx.android.synthetic.main.fragment_melhores_series.*
import kotlinx.android.synthetic.main.fragment_melhores_series.view.*

class MelhoresSeriesFragment : Fragment(), MelhoresSeriesAdapter.onMelhoresSerieClickListener {

    private lateinit var melhoresSeriesAdapter: MelhoresSeriesAdapter
    private lateinit var melhoresSeriesLayoutManager: LinearLayoutManager
    private lateinit var viewModelAssistirMaisTarde: AssistirMaisTardeViewModel
    var topSeriesList = arrayListOf<ResultTv>()
    var mediaList = arrayListOf<ResultTv>()

    val viewModel by viewModels<MelhoresSeriesViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MelhoresSeriesViewModel(service) as T
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
        val view = inflater.inflate(R.layout.fragment_melhores_series, container, false)

        viewModelAssistirMaisTarde =
            ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        //Iniciando o ReciclerView de Melhores Séries
        melhoresSeriesLayoutManager = LinearLayoutManager(context)
        melhoresSeriesAdapter = MelhoresSeriesAdapter(this)
        view.rc_melhoresSeriesList.layoutManager = melhoresSeriesLayoutManager
        view.rc_melhoresSeriesList.adapter = melhoresSeriesAdapter
        view.rc_melhoresSeriesList.isHorizontalFadingEdgeEnabled
        view.rc_melhoresSeriesList.setHasFixedSize(true)

        if(testConnection() == true) {
            setDataOnline()
        }else{
            Toast.makeText(context, R.string.reportingOffline, Toast.LENGTH_SHORT).show()
            setDataOffline()
        }

        return view
    }

    fun setDataOnline(){
        //Recebe a lista de melhores séries da API
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            topSeriesList = it.results
            viewModel.getAssistirMaisTardeListInCloud()
        }

        //Recebendo os Itens (Filmes e Séries) que estão na lista de Assistir Mais Tarde e compara
        //com as séries retornadas pela API
        viewModel.returnAssistirMaisTardeList.observe(viewLifecycleOwner) {
            mediaList = viewModel.checkInAssistirMaisTardeList(
                topSeriesList,
                it
            )
            viewModel.getAcompanhadoList()
        }

        //Recebendo as Séries que o usuário está Acompanhando e compara com as séries retornadas
        viewModel.returnAcompanhandoList.observe(viewLifecycleOwner) {
            mediaList = viewModel.checkSerieInAcompanhandoList(mediaList, it)
            pb_melhoresSeries.setVisibility(View.INVISIBLE)
            melhoresSeriesAdapter.addList(mediaList)
        }

        viewModel.getTopSeriesList()
    }

    fun setDataOffline(){

    }

    override fun melhoresItemClick(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", media.poster_path)
            intent.putExtra("movie", false)
            intent.putExtra("id", media.id)

            startActivity(intent)
        }
    }

    override fun saveInAssistirMaisTardeList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            viewModel.saveInAssistirMaisTardeList(media)
            Toast.makeText(context, "Série adicionada a Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfAssistirMaisTardeList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            viewModel.deleteFromAssistirMaisTardeList(media)
            Toast.makeText(context, "Série removida da Agenda", Toast.LENGTH_SHORT).show()
        }
    }

    override fun saveInAcompanhandoList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            viewModel.saveInAcompanhandoList(media)
            Toast.makeText(context, "Acompanhando: ${media.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun removeOfAcompanhandoList(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            viewModel.deleteFromAcompanhandoList(media)
            Toast.makeText(
                context,
                "Você não está mais acompanhando: ${media.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun share(position: Int) {
        viewModel.returnTopSeriesAPI.observe(viewLifecycleOwner) {
            var mediaList = it.results
            var media = mediaList.get(position)

            var ShareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(
                    Intent.EXTRA_TEXT,
                    "${media.name} está em ${position + 1}º na lista de Melhores Séries do Filmapp!"
                )
                this.type = "text/plain"
            }
            startActivity(ShareIntent)
        }
    }

    fun testConnection(): Boolean {
        val cm = activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}