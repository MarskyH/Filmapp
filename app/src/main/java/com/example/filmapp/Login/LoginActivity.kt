package com.example.filmapp.Login


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.R
import com.example.filmapp.Series.Ui.SerieSelectedActivity
import kotlinx.android.synthetic.main.layout_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        imageView.setOnClickListener{
            val intent = Intent(this, SerieSelectedActivity::class.java)
            startActivity(intent)
        }

    }
}