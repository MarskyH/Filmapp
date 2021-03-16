package com.example.filmapp.home.agenda

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Media.UI.MediaSelectedActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.*
import kotlinx.android.synthetic.main.fragrecycler_assistirmaistarde.view.*

class FragRecycler_asssistirMaisTarde : Fragment(),
    AssistirMaisTardeAdapter.onAssistirMaisTardeItemClickListener {

    private lateinit var mediaListAdapter: AssistirMaisTardeAdapter
    private lateinit var mediaListLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: AssistirMaisTardeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragrecycler_assistirmaistarde, container, false)

        viewModel = ViewModelProvider(this).get(AssistirMaisTardeViewModel::class.java)

        //Iniciando o ReciclerView Assistir Mais Tarde
        mediaListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mediaListAdapter = AssistirMaisTardeAdapter(this)
        view.rv_assistirMaisTarde.layoutManager = mediaListLayoutManager
        view.rv_assistirMaisTarde.adapter = mediaListAdapter
        view.rv_assistirMaisTarde.isHorizontalFadingEdgeEnabled
        view.rv_assistirMaisTarde.setHasFixedSize(true)

        if(testConnection() == true) {
            setDataOnline()
        }else{
            Toast.makeText(context, R.string.reportingOffline, Toast.LENGTH_SHORT).show()
            setDataOffline()
        }

        return view
    }

    fun setDataOnline(){
        mediaListAdapter.isClickable = true

        viewModel.getAssistirMaisTardeListInCloud()

        viewModel.returnAssistirMaisTardeList.observe(viewLifecycleOwner){
            if(it.size == 0){
                pb_assistirMaisTarde.setVisibility(View.GONE)
                tv_titleAssistirMaisTarde.setVisibility(View.GONE)
            }else{
                pb_assistirMaisTarde.setVisibility(View.INVISIBLE)
                mediaListAdapter.addList(it)
            }
        }
    }

    fun setDataOffline(){
        mediaListAdapter.isClickable = false

        viewModel.getAssistirMaisTardeListInCloud()

        viewModel.returnAssistirMaisTardeList.observe(viewLifecycleOwner){
            if(it.size == 0){
                pb_assistirMaisTarde.setVisibility(View.GONE)
                tv_titleAssistirMaisTarde.setVisibility(View.GONE)
            }else{
                pb_assistirMaisTarde.setVisibility(View.INVISIBLE)
                mediaListAdapter.addList(it)
            }
        }
    }

    companion object{
        fun newInstance() = FragRecycler_asssistirMaisTarde()
    }

    override fun assistirMaisTardeItemClick(position: Int) {
        viewModel.returnAssistirMaisTardeList.observe(viewLifecycleOwner) {
            var media = it.get(position)

            val intent = Intent(context, MediaSelectedActivity::class.java)
            intent.putExtra("poster", media.poster_path)

            if(media.type == "Movie")
                intent.putExtra("movie", true)
            else
                intent.putExtra("movie", false)

            intent.putExtra("id", media.id)

            startActivity(intent)
            activity?.finish()
        }
    }

    fun testConnection(): Boolean {
        val cm = activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

}