package com.niran.noteapplication.utils

import com.niran.noteapplication.utils.NoteUtils.Companion.toNote
import org.junit.Before
import org.junit.Test

class NoteUtilsTest {

    private lateinit var title: String
    private lateinit var text: String

    @Before
    fun setUp(){
        title = "hey! this is a new note"
        text = "very good\nit's a really good thing that I met you"
    }

    @Test
    fun `note title equals to text when short text`() {
        val note = title.toNote()
        assert(note.noteTitle == title)
    }

    @Test
    fun `note text is empty when short text`() {
        val note = title.toNote()
        assert(note.noteText.isEmpty())
    }

    @Test
    fun `note title equals start of the text when long text`() {
        val note = "$title\n$text".toNote()
        assert(note.noteTitle == title)
    }

    @Test
    fun `note text equals part of the text when long text`() {
        val note = "$title\n$text".toNote()
        assert(note.noteText == text)
    }
}