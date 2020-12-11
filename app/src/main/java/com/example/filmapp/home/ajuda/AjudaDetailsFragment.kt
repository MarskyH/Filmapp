package com.example.filmapp.home.ajuda

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.R
import com.example.filmapp.Services.service

class AjudaDetailsFragment : Fragment() {

    val viewModel by viewModels<AjudaDetailsViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AjudaDetailsViewModel(service) as T
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
        var view = inflater.inflate(R.layout.fragment_ajuda_details, container, false)

        return view
    }

    companion object {
        fun newInstance() = AjudaDetailsFragment()
    }
}