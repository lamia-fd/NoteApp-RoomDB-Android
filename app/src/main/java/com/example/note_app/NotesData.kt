package com.example.note_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Notes")
data class NotesData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id:Int=0,
    @ColumnInfo(name = "note") val note:String
)
