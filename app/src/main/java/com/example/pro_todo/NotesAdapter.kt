package com.example.taskapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.pro_todo.R

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, note)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, note: Note) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ ->
                // User confirmed deletion
                db.deleteNote(note.id)
                refreshData(db.getAllNotes())
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
                // User cancelled deletion
                Toast.makeText(context, "Deletion cancelled", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }

}