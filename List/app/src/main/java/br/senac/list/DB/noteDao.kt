package br.senac.list.DB

import androidx.room.*
import br.senac.list.model.Note

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
}