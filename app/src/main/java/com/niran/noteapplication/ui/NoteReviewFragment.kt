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

/**
 * you can get to this fragment only by...
 * ...clicking on a note...
 * ...so note must not be null
 */
class NoteReviewFragment : Fragment() {

    private lateinit var binding: FragmentNoteReviewBinding

    private lateinit var note: Note

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteReviewBinding.inflate(inflater)

        note = viewModel.note!!

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = this@NoteReviewFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
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
}