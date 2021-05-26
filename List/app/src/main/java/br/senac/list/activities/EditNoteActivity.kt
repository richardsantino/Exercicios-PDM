package br.senac.list.activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import br.senac.list.DB.AppDataBase
import br.senac.list.R
import br.senac.list.databinding.ActivityNewNoteBinding
import br.senac.list.model.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class EditNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var binding: ActivityNewNoteBinding
    lateinit var colorChoosen: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var note: Note
        val noteId = intent.getIntExtra("NoteId", 1)

        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            note = db.noteDao().listNote(noteId)
            runOnUiThread {
                updateScreen(note)
            }
        }.start()


        binding.btnColor.setOnClickListener {
            val dialog = ColorPickerDialog.newBuilder().setColor(Color.WHITE).create()
            dialog.show(supportFragmentManager, "COLOR_PICKER_EDT")
        }

        binding.btnAdd.setOnClickListener {
            note.title = binding.etTitle.text.toString()
            note.desc = binding.etDesc.text.toString()
            note.noteColor = colorChoosen
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

    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        val colorPicked = Integer.toHexString(color)
        colorChoosen = "#$colorPicked"

    }
}