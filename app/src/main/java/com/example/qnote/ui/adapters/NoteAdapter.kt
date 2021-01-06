package com.example.qnote.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qnote.R
import com.example.qnote.core.models.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes = listOf<Note>()
    val a = 0
    private lateinit var onNoteClickListener: OnNoteClickListener

    fun setOnNoteClickListener(onNoteClickListener: OnNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun setNotes(notesFromDB: List<Note>) {
        notes = notesFromDB
        notifyDataSetChanged()
    }

//    fun addNotes(notes: ArrayList<Note>) {
//        Log.i("res", "adding notes")
//        this.notes.addAll(notes)
//        notifyDataSetChanged()
//    }
//
//    fun addNote(note: Note) {
//        Log.i("res", "adding note to adapter")
//        notes.add(note)
//        notifyDataSetChanged()
//        Log.i("res", "note to adapter success")
//    }

    inner class NoteViewHolder public constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)

        init {
            itemView.setOnClickListener {
                onNoteClickListener.onShortClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                onNoteClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(note: Note) {
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewDate.text = note.date
        }

//        companion object {
//            fun from(parent: ViewGroup) : NoteViewHolder {
//                val view: View = LayoutInflater.from(parent.context).inflate(
//                    R.layout.note_item,
//                    parent,
//                    false)
//
//
//                return NoteViewHolder(view)
//            }
//        }
    }

}