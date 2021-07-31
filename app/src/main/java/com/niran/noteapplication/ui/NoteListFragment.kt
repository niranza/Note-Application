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
import com.niran.noteapplication.databinding.FragmentNoteListBinding
import com.niran.noteapplication.utils.NoteAdapter


class NoteListFragment : Fragment() {

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as NoteApplication).noteRepository)
    }

    private lateinit var binding: FragmentNoteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter(object : NoteAdapter.NoteClickHandler {
            override fun onNoteClick(note: Note) {
                viewModel.note = note
                navigateToNoteReviewFragment()
            }
        })

        binding.noteRv.adapter = adapter
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        viewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            noteList?.let {
                adapter.submitList(it)
            }
        }
    }

    fun navigateToAddNoteFragment() {
        viewModel.note = null
        view?.findNavController()
            ?.navigate(NoteListFragmentDirections.actionNoteListFragmentToAddNoteFragment())
    }

    private fun navigateToNoteReviewFragment() {
        view?.findNavController()
            ?.navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteReviewFragment())
    }
}