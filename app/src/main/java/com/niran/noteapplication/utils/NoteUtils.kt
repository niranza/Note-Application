package com.niran.noteapplication.utils

import com.niran.noteapplication.database.models.Note

class NoteUtils {
    companion object {
        fun String.toNote(noteId: Long = 0L): Note {
            val paragraphList = split("\n").toMutableList()
            var noteTitle = ""
            if (isNotBlank()) noteTitle = paragraphList.removeAt(0).trim()
            val noteText = paragraphList.joinToString("\n").trim()
            return Note(noteId = noteId, noteTitle = noteTitle, noteText = noteText)
        }

        fun Note.toEditableText(): String = "$noteTitle\n$noteText"
    }
}