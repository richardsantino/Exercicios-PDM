package br.senac.list.activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import br.senac.list.DB.AppDataBase
import br.senac.list.R
import br.senac.list.databinding.ActivityEditNoteBinding
import br.senac.list.databinding.ActivityNewNoteBinding
import br.senac.list.model.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog

class EditNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var note: Note

        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            note = db.noteDao().listNote(1)
            runOnUiThread {
                updateScreen(note)
            }
        }.start()


        binding.btnColor.setOnClickListener {
            note.noteColor = ColorPickerDialog.newBuilder().setColor(Color.WHITE).show(this).toString()
        }

        binding.btnAdd.setOnClickListener {
            note.title = binding.etTitle.text.toString()
            note.desc = binding.etDesc.text.toString()
            Thread{
                updateNote(note)
                finish()
            }.start()
        }
    }

    fun updateScreen(note: Note){
        binding.etTitle.setText(note.title)
        binding.etDesc.setText(note.desc)
    }


    fun updateNote(note: Note) {
        val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
        db.noteDao().update(note)
    }
}