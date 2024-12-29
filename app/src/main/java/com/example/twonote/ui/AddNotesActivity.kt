package com.example.twonote.ui

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.twonote.databinding.ActivityAddNotesBinding
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.local.local.dao.NotesDao
import com.example.twonote.data.model.DBNotesModel
import ir.amozeshgam.persiandate.PersianDate

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val type = intent.getBooleanExtra("newNote", false)
        val id = intent.getIntExtra("notesId", 0)


        val dao = NotesDao(DBHelper(this))



        if (type) {
            binding.txtDate.text = getDate()
        } else {
            val note = dao.getNoteById(id)
            binding.edtTitleNotes.text = Editable.Factory().newEditable(note.title)
            binding.edtDetailsNote.text = Editable.Factory().newEditable(note.details)
            binding.txtDate.text = note.date
        }


        binding.btnSave.setOnClickListener {

            val title = binding.edtTitleNotes.text.toString()
            val details = binding.edtDetailsNote.text.toString()

            if (title.isNotEmpty()) {
                val note = DBNotesModel(
                    0,
                    title,
                    details,
                    DBHelper.FALSE_STATE,
                    getDate()
                )
                val result = if (type) {
                    dao.saveNotes(note)
                } else {
                    dao.editNotes(id, note)
                }


                if (result) {
                    showText("با موفقیت ثبت شد.")

                    finish()
                } else {
                    showText("خطا در ثبت یادداشت")
                }

            } else
                showText("عنوان نمی‌تواند خالی باشد")
        }




        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun getDate(): String {

        val date = PersianDate()

        val currentDate = "${date.year}/${date.month}/${date.day}"
        val currentTime = "${date.hour}/${date.min}/${date.second}"

        return "$currentDate | $currentTime"

    }



    private fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    }
}