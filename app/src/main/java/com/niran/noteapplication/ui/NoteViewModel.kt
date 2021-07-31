package com.niran.noteapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val noteList = noteRepository.noteList.asLiveData()

    var note: Note? = null

    fun updateNote(note: Note, text: String) {
        viewModelScope.launch {
            val updatedNote = note.copy(noteText = text)
            noteRepository.updateNote(updatedNote)
        }
    }

    fun insertNote(noteText: String) {
        viewModelScope.launch {
            noteRepository.insertNote(Note(noteText = noteText))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}

class NoteViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}