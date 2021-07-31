package com.niran.noteapplication.repositories

import android.util.Log
import com.niran.noteapplication.database.daos.NoteDao
import com.niran.noteapplication.database.models.Note

class NoteRepository(private val noteDao: NoteDao) {

    val noteList = noteDao.getAllNotes()

    suspend fun updateNote(note: Note) {
        Log.d("TAG", "updating note: ${note.id}. ${note.noteText}")
        noteDao.updateNote(note)
        Log.d("TAG", "note updated: ${note.id}. ${note.noteText}")
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

}