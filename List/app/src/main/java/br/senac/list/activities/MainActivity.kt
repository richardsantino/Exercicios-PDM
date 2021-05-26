package br.senac.list.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.senac.list.R
import br.senac.list.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEx10.setOnClickListener {
            val i = Intent(this, ListNotesActivity::class.java)
            startActivity(i)
        }

        binding.btnEx11.setOnClickListener {
            val i = Intent(this, CepSearchActivity::class.java)
            startActivity(i)
        }

    }
}