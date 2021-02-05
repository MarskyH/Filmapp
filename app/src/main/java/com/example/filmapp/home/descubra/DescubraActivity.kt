package com.example.filmapp.home.descubra

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmapp.Configuracoes.ConfiguracoesActivity
import com.example.filmapp.R
import com.example.filmapp.Services.service
import kotlinx.android.synthetic.main.activity_descubra.*
import kotlinx.android.synthetic.main.activity_historico.*
import org.w3c.dom.Text

class DescubraActivity : AppCompatActivity() {

    val viewModel by viewModels<DescubraViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DescubraViewModel(service) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descubra)

        if(testConnection() == true) {
            setDataOnline()
        }else{
            Toast.makeText(this, "Você está offline", Toast.LENGTH_SHORT).show()
            setDataOffline()
        }

        textInput_search.setEndIconOnClickListener {
            callResultsSearch()
        }

        textInput_search.editText?.doOnTextChanged { inputText, _, _, _ ->
            callResultsSearch()
        }

        setSupportActionBar(toolbarDescubraPage)

        toolbarDescubraPage.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun setDataOnline(){

    }

    fun setDataOffline(){

    }

    //Usado para add o Menu a Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_config, menu)
        return true
    }

    //Usado pra add ações de click aos itens do Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configurações_toolbarConfig -> {
                callConfiguracoesPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Usando para add ações de click as teclas do teclado
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    callResultsSearch()
                    true
                }
                KeyEvent.KEYCODE_EXPLORER -> {
                    callResultsSearch()
                    true
                }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    //Método usado para esconder o teclado quando ele perde o foco
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun callConfiguracoesPage() {
        val intent = Intent(this, ConfiguracoesActivity::class.java)
        startActivity(intent)
    }

    fun callResultsSearch() {
        var searchText = textInput_search.editText?.text.toString()

        if(testConnection() == true){
            if (searchText != "") {
                //Inflando o RecyclerView de Resultados - Filmes (fragRecycler_filmesDescubra)
                supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragRecycler_filmesDescubraSpace,
                        FragRecycler_filmesDescubra.newInstance(searchText)
                    )
                    commit()
                }

                //Inflando o RecyclerView de Resultados - Series (fragRecycler_filmesDescubra)
                supportFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.fragRecycler_seriesDescubraSpace,
                        FragRecycler_seriesDescubra.newInstance(searchText)
                    )
                    commit()
                }
            }
        }
    }

    fun testConnection(): Boolean {
        val cm = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected

    }

}