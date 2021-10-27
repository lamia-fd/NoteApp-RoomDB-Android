package com.example.note_app

import androidx.room.*


@Dao
interface NoteDao {

    @Query("select * from Notes") fun getAllNote():List<NotesData>

    @Insert fun addNote(note:NotesData)

    @Query("delete from Notes where id= :id") fun deleteNote(id: Int)
    @Query("UPDATE Notes SET note =:note WHERE id=:id;") fun updateNote(id: Int,note:String)
}