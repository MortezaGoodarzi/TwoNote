package com.example.twonote.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.twonote.R
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.local.local.dao.NotesDao
import com.example.twonote.data.model.RecyclerNoteModel
import com.example.twonote.databinding.ListItemNotesBinding
import com.example.twonote.ui.AddNotesActivity

class NotesAdapter(
    private val context: Activity,
    private val dao: NotesDao
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var allData: ArrayList<RecyclerNoteModel>

    init {
        allData = dao.getNotesForRecycler(DBHelper.TRUE_STATE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesViewHolder(ListItemNotesBinding.inflate(LayoutInflater.from(context), parent, false))


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    override fun getItemCount() = allData.size

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: ArrayList<RecyclerNoteModel>) {
        allData = data
        notifyDataSetChanged()
    }


    inner class NotesViewHolder(
        private val binding: ListItemNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: RecyclerNoteModel) {
            binding.txtTitleNotes.text = data.title

            binding.imgDeleteNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle("delete")
                    .setMessage("می‌خوای حذفش کنی؟")
                    .setIcon(R.drawable.baseline_delete_24)
                    .setNegativeButton("نه") { _, _ -> }
                    .setPositiveButton("بله") { _, _ ->
                        dao.editNotes(data.id, DBHelper.TRUE_STATE)
                        allData.removeAt(layoutPosition)
                        notifyItemRemoved(layoutPosition)
                        Toast.makeText(context, "پاک شد", Toast.LENGTH_SHORT).show()
                    }.create()
                    .show()
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, AddNotesActivity::class.java)
                intent.putExtra("notesId", data.id)
                context.startActivity(intent)
            }
        }

    }

}