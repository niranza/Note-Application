package com.niran.noteapplication.repositories

import com.niran.noteapplication.database.daos.NoteDao
import com.niran.noteapplication.database.models.Note

class NoteRepository(private val noteDao: NoteDao) {

    val noteList = noteDao.getAllNotes()

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

}