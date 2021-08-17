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
import com.niran.noteapplication.utils.FragmentUtils.Companion.hideKeyBoard
import com.niran.noteapplication.utils.NoteUtils.Companion.toEditableText
import com.niran.noteapplication.utils.NoteUtils.Companion.toNote
import com.niran.noteapplication.viewmodels.NoteViewModel
import com.niran.noteapplication.viewmodels.NoteViewModelFactory


class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val isEditingExistingNote get() = viewModel.note != null

    private var note: Note? = null

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNoteBinding.inflate(inflater)

        if (isEditingExistingNote) note = viewModel.note

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fragment = this@AddNoteFragment
            lifecycleOwner = viewLifecycleOwner
            if (isEditingExistingNote) newNoteEt.setText(note?.toEditableText())
        }
    }

    fun saveNote() = binding.newNoteEt.text.toString().apply {
        if (isBlank()) {
            if (isEditingExistingNote) note?.let { viewModel.deleteNote(it) }
            navigateToNoteListFragment()
            return@apply
        } else viewModel.insertNote(toNote(note?.noteId ?: 0))

        navigateToNoteListFragment()
    }

    private fun navigateToNoteListFragment() = view?.findNavController()
        ?.navigate(AddNoteFragmentDirections.actionAddNoteFragmentToNoteListFragment())

    override fun onDestroy() {
        super.onDestroy()
        hideKeyBoard()
    }
}