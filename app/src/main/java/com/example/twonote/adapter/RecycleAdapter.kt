package com.example.twonote.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.twonote.R
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.local.local.dao.NotesDao
import com.example.twonote.data.model.RecyclerNoteModel
import com.example.twonote.databinding.ListItemRecycleBinBinding


class RecycleAdapter(
    private val context: Activity,
    private val dao: NotesDao
) : RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder>() {

    private val allData = dao.getNotesForRecycler(DBHelper.TRUE_STATE       )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecycleViewHolder(ListItemRecycleBinBinding.inflate(LayoutInflater.from(context), parent, false))


    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    override fun getItemCount() = allData.size




    inner class RecycleViewHolder(
        private val binding: ListItemRecycleBinBinding
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
                        dao.deleteNotes(data.id)
                        allData.removeAt(layoutPosition)
                        notifyItemRemoved(layoutPosition)
                        Toast.makeText(context, "پاک شد", Toast.LENGTH_SHORT).show()
                    }.create()
                    .show()
            }


            binding.imgRestoreNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle("restore")
                    .setMessage("می‌خوای برگردونیش؟")
                    .setIcon(R.drawable.baseline_delete_24)
                    .setNegativeButton("نه") { _, _ -> }
                    .setPositiveButton("بله") { _, _ ->
                        dao.editNotes(data.id, DBHelper.FALSE_STATE)
                        allData.removeAt(layoutPosition)
                        notifyItemRemoved(layoutPosition)
                        Toast.makeText(context, "برگشت", Toast.LENGTH_SHORT).show()
                    }.create()
                    .show()
            }
        }

    }

}