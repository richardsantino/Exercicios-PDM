package br.senac.list.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senac.list.R
import br.senac.list.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.btnEx10.setOnClickListener {
            val i = Intent(this, )
            startActivity(i)
        }

        binding.btnEx11.setOnClickListener {
            val i = Intent(this, )
            startActivity(i)
        }*/

    }
}