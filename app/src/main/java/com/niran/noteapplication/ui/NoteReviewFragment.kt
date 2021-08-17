package com.niran.noteapplication.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.niran.noteapplication.NoteApplication
import com.niran.noteapplication.R
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.database.models.sendNoteNotification
import com.niran.noteapplication.databinding.FragmentNoteReviewBinding
import com.niran.noteapplication.utils.FragmentUtils.Companion.showUndoSnackBar
import com.niran.noteapplication.utils.NoteUtils.Companion.toEditableText
import com.niran.noteapplication.viewmodels.NoteViewModel
import com.niran.noteapplication.viewmodels.NoteViewModelFactory

/**
 * you can get to this fragment only by...
 * ...clicking on a note...
 * ...so note must not be null
 */
class NoteReviewFragment : Fragment() {

    private var _binding: FragmentNoteReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var note: Note

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteReviewBinding.inflate(inflater)

        note = viewModel.note!!

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            noteReviewTv.text = viewModel.note?.toEditableText()

            root.setOnClickListener { navigateToAddNoteFragment() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_note -> {
                navigateToAddNoteFragment()
                true
            }
            R.id.delete_note -> {
                viewModel.deleteNote(note)
                showUndoSnackBar { viewModel.insertNote(note) }
                navigateToNoteListFragment()
                true
            }
            R.id.remind_note -> {
                note.sendNoteNotification(requireContext())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToAddNoteFragment() = view?.findNavController()
        ?.navigate(NoteReviewFragmentDirections.actionNoteReviewFragmentToAddNoteFragment())

    private fun navigateToNoteListFragment() = view?.findNavController()
        ?.navigate(NoteReviewFragmentDirections.actionNoteReviewFragmentToNoteListFragment())

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}