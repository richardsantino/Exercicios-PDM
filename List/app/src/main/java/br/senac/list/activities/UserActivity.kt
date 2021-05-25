package br.senac.list.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senac.list.R
import br.senac.list.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefs = getSharedPreferences("Users", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "") as String

        binding.etUsername.setText(username)

        binding.btnSave.setOnClickListener {
            val editor = sharedPrefs.edit()

            editor.putString("username", binding.etUsername.text.toString())
            editor.commit()
        }
    }
}