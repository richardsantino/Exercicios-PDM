package br.senac.list.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senac.list.R
import br.senac.list.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}