package com.niran.noteapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.niran.noteapplication.NoteApplication
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.databinding.FragmentNoteListBinding
import com.niran.noteapplication.utils.FragmentUtils.Companion.showUndoSnackBar
import com.niran.noteapplication.utils.adapters.NoteAdapter
import com.niran.noteapplication.viewmodels.NoteViewModel
import com.niran.noteapplication.viewmodels.NoteViewModelFactory


class NoteListFragment : Fragment() {

    private val noteViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val noteAdapter = NoteAdapter(object : NoteAdapter.NoteClickHandler {
                override fun onNoteClick(note: Note) {
                    noteViewModel.note = note
                    navigateToNoteReviewFragment()
                }
            })

            noteRv.adapter = noteAdapter
            viewModel = noteViewModel
            fragment = this@NoteListFragment
            lifecycleOwner = this@NoteListFragment

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    noteAdapter.currentList[viewHolder.adapterPosition].also { note ->
                        noteViewModel.deleteNote(note)
                        showUndoSnackBar { noteViewModel.insertNote(note) }
                    }
                }
            }).apply { attachToRecyclerView(noteRv) }

            noteViewModel.noteList.observe(viewLifecycleOwner) { noteList ->
                noteList?.let {
                    if (it.isEmpty()) showNoNotes() else hideNoNotes()
                    noteAdapter.submitList(it)
                }
            }
        }
    }

    private fun showNoNotes() = binding.apply { tvNoNotes.visibility = View.VISIBLE }

    private fun hideNoNotes() = binding.apply { tvNoNotes.visibility = View.GONE }

    fun navigateToAddNoteFragment() {
        noteViewModel.note = null
        view?.findNavController()
            ?.navigate(NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment())
    }

    private fun navigateToNoteReviewFragment() {
        view?.findNavController()
            ?.navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteReviewFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}