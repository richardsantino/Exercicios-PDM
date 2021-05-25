package br.senac.list.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import br.senac.list.model.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun noteDao(): noteDao
}