package br.senac.list.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager
import androidx.room.Room
import br.senac.list.DB.AppDataBase
import br.senac.list.R
import br.senac.list.databinding.ActivityListNotesBinding
import br.senac.list.databinding.NoteCardBinding
import br.senac.list.model.Note
import java.util.stream.DoubleStream.builder

class ListNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityListNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val i = Intent(this, NewNoteActivity::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()

        refreshNotes()
    }

    fun refreshNotes(){
        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            val notes = db.noteDao().listAll()
            runOnUiThread {
                updateUi(notes)
            }
        }.start()
    }

    fun updateUi(notes: List<Note>){
        binding.container.removeAllViews()

        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefManager.getInt("textColor", R.color.gray)

        val tSize = prefManager.getString("titleSize", 19.toString()) as String
        var titleSize: Float

        val dSize = prefManager.getString("descSize", 14.toString()) as String
        var descSize: Float

        notes.forEach {
            val cardBinding = NoteCardBinding.inflate(layoutInflater)
            val note = it

            cardBinding.txtTitle.text = it.title
            cardBinding.txtDesc.text = it.desc
            cardBinding.txtUser.text = it.user

            cardBinding.txtTitle.setTextColor(color)
            if(tSize.isDigitsOnly()){
                titleSize = tSize.toFloat()
                cardBinding.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
            }

            cardBinding.txtDesc.setTextColor(color)
            if(dSize.isDigitsOnly()){
                descSize = dSize.toFloat()
                cardBinding.txtDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, descSize)
            }

            cardBinding.txtUser.setTextColor(color)

            cardBinding.root.setCardBackgroundColor(Color.parseColor(it.noteColor))

            cardBinding.btnDelete.setOnClickListener {
                Thread{
                    val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
                    db.noteDao().delete(note)
                }.start()
                recreate()
            }

            cardBinding.btnEdit.setOnClickListener {

            }

            binding.container.addView(cardBinding.root)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.user ->{
                val i = Intent(this, UserActivity::class.java)
                startActivity(i)
            }
            R.id.config ->{
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}