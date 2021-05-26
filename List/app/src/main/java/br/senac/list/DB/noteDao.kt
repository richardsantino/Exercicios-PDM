package br.senac.list.DB

import androidx.room.*
import br.senac.list.model.Note
import java.util.*

@Dao
interface noteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query(value = "select * from Note")
    fun listAll(): List<Note>

    @Query(value = "select * from Note where id = :id")
    fun listNote(id: Int): Note
}