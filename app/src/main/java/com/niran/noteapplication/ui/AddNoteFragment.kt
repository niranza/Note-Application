package com.niran.noteapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.niran.noteapplication.NoteApplication
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.databinding.FragmentAddNoteBinding
import com.niran.noteapplication.utils.AppUtils


class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    private var isEditingExistingNote: Boolean = false

    private lateinit var note: Note

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater)

        val nullableNote = viewModel.note
        isEditingExistingNote = nullableNote != null
        if (isEditingExistingNote) note = nullableNote!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fragment = this@AddNoteFragment
            lifecycleOwner = viewLifecycleOwner
            if (isEditingExistingNote) newNoteEt.setText(note.noteText)
        }
    }

    fun hideKeyboard() = AppUtils.hideKeyBoard(requireActivity())

    fun saveNote() {
        val text = binding.newNoteEt.text.toString()
        if (text.isBlank()) {
            if (isEditingExistingNote) viewModel.deleteNote(note)
            navigateToNoteListFragment()
            return
        }

        if (isEditingExistingNote) viewModel.updateNote(note, text)
        else viewModel.insertNote(text)

        navigateToNoteListFragment()
    }

    private fun navigateToNoteListFragment() =
        view?.findNavController()
            ?.navigate(AddNoteFragmentDirections.actionAddNoteFragmentToNoteListFragment())

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }
}