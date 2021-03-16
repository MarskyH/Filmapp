package com.example.filmapp.Configuracoes

import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmapp.R
import com.example.filmapp.Services.service
import com.example.filmapp.home.acompanhando.AcompanhandoViewModel
import kotlinx.android.synthetic.main.activity_agenda.*
import kotlinx.android.synthetic.main.activity_report_error.*

class ReportErrorActivity : AppCompatActivity() {

    var error = "Erro não informado"
    var errorComponent = "Erro não informado"

    val viewModel by viewModels<ReportErrorViewModel>{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ReportErrorViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_error)

        var dados = intent.extras
        var isForwarded = dados?.getBoolean("isForwarded")

        //Caso seja um report encaminhando, já se tem alguns dados do erro
        if(isForwarded == true){
            error = dados?.getString("error").toString()
            errorComponent = dados?.getString("errorComponent").toString()
            editText_errorLocation.setText(dados?.getString("errorLocation"))
        }

        Log.i("venho", isForwarded.toString())

        setSupportActionBar(toolbarReportErrorPage)

        toolbarReportErrorPage.setNavigationOnClickListener {
            finish()
        }

        btn_enviar.setOnClickListener {
            viewModel.sendErrorReport(
                input_errorLocation.editText?.text.toString(),
                input_errorDescription.editText?.text.toString(),
                error,
                errorComponent
            )

            Toast.makeText(this, "Erro reportado, em breve o problema será resolvido", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    fun testConnection(): Boolean {
        val cm = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}