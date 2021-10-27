package com.example.note_app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [NotesData::class],version = 1,exportSchema = false)
abstract class NoteDataBase: RoomDatabase() {

    companion object {

        var instance: NoteDataBase? = null

        fun getInstance(context: Context): NoteDataBase {
            if (instance != null) {
                return instance as NoteDataBase

            }
            instance = databaseBuilder(
                context,
                NoteDataBase::class.java,
                "NoteDatabase"
            ).run { allowMainThreadQueries() }.build()

            return instance as NoteDataBase

        }
    }
    abstract fun NoteDao():NoteDao
}