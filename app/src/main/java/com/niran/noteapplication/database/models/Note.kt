package com.niran.noteapplication.database.models

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.niran.noteapplication.NotificationUtil

@Entity(tableName = "note_table")
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "note_text")
    val noteText: String

        )

fun Note.sendNoteNotification(context: Context){
    NotificationUtil.sendNotification(noteText, context)
}