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

class NewNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var binding: ActivityNewNoteBinding
    lateinit var colorChoosen: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        colorChoosen = "#FFFFFF"

        binding.btnColor.setOnClickListener {
            val dialog = ColorPickerDialog.newBuilder().setColor(Color.WHITE).create()
            dialog.show(supportFragmentManager, "COLOR_PICKER_EDT")
        }

        binding.btnAdd.setOnClickListener {
            val sharedPrefs = getSharedPreferences("Users", Context.MODE_PRIVATE)
            val user = sharedPrefs.getString("username", "") as String


            val note = Note(title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString(),
                user = user, noteColor = colorChoosen)
            Thread{
                insertNote(note)
                finish()
            }.start()
        }
    }

    fun insertNote(note: Note){
        val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
        db.noteDao().insert(note)
    }

    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        val colorPicked = Integer.toHexString(color)
        colorChoosen = "#$colorPicked"

    }


}
