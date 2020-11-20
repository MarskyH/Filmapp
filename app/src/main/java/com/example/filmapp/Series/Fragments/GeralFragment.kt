package com.example.filmapp.Series.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R


class GeralFragment : Fragment() {
//    private var progr = 0
//    private lateinit var cma: ContractSerieSelectedActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_series_geral, container, false)
//        cma.callGeralFrag()
   //     incrCircleBar()


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       // if (context is ContractSerieSelectedActivity) cma = context
    }

    companion object {
        fun newInstance() = GeralFragment()

    }
//    fun incrCircleBar(){
//
//            if(progr <= 90){
//                progr += 10
//                updateProgressBar()
//            }
//
//        }
//
//    fun updateProgressBar(){
//        progress_circular.progress = progr
//        tvProgress.text = "$progr%"
//    }
}
