package com.niran.noteapplication.database.models

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.niran.noteapplication.utils.NotificationUtil

@Entity(tableName = "note_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId: Long = 0L,

    @ColumnInfo(name = "note_title")
    val noteTitle: String = "",

    @ColumnInfo(name = "note_text")
    val noteText: String = ""
)

fun Note.sendNoteNotification(context: Context) {
    NotificationUtil.sendNotification(noteTitle, context)
}