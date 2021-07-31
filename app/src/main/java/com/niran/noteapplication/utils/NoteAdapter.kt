package com.niran.noteapplication.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.niran.noteapplication.database.models.Note
import com.niran.noteapplication.databinding.NoteItemBinding

class NoteAdapter(private val clickHandler: NoteClickHandler) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(Comparator) {

    class NoteViewHolder private constructor(
        private val binding: NoteItemBinding,
        private val clickHandler: NoteClickHandler
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                this.note = note
                noteTv.setOnClickListener { clickHandler.onNoteClick(note) }
                executePendingBindings()
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickHandler: NoteClickHandler): NoteViewHolder {
                val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
                return NoteViewHolder(binding, clickHandler)
            }
        }
    }

    interface NoteClickHandler {
        fun onNoteClick(note: Note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent, clickHandler)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object Comparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteText == newItem.noteText
        }
    }
}