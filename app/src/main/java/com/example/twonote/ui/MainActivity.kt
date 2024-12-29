package com.example.twonote.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twonote.adapter.NotesAdapter
import com.example.twonote.databinding.ActivityMainBinding
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.local.local.dao.NotesDao

class MainActivity : AppCompatActivity() {
    private lateinit var dao: NotesDao
    private lateinit var adapter: NotesAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        binding.imgAddNotes.setOnClickListener {
            val intent = Intent(this, AddNotesActivity::class.java)
            intent.putExtra("newNote", true)
            startActivity(intent)
        }

        binding.txtRecycleBin.setOnClickListener {
            val intent = Intent(this, RecycleBinActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        val data = dao.getNotesForRecycler(DBHelper.FALSE_STATE)
        adapter.changeData(data)
    }


    private fun initRecycler() {
        dao = NotesDao(DBHelper(this))
        val data = dao.getNotesForRecycler(DBHelper.FALSE_STATE)
        adapter = NotesAdapter(this, dao)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }
}