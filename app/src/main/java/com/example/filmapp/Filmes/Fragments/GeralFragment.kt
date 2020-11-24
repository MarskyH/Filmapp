package com.example.filmapp.Filmes.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.Filmes.Interfaces.ContractFilmeSelectedActivity
import com.example.filmapp.R
import kotlinx.android.synthetic.main.fragment_series_geral.*


class GeralFragment : Fragment() {
    private var progr = 0
    private lateinit var cma: ContractFilmeSelectedActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_filmes_geral, container, false)
        cma.callGeralFrag()
        incrCircleBar()


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContractFilmeSelectedActivity) cma = context
    }

    companion object {
        fun newInstance() = GeralFragment()

    }
    fun incrCircleBar(){

            if(progr <= 90){
                progr += 10
                updateProgressBar()
            }

        }

    fun updateProgressBar(){
        progress_circular.progress = progr
        tvProgress.text = "$progr%"
    }
}
