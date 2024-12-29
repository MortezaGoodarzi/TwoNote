package com.example.twonote.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twonote.adapter.NotesAdapter
import com.example.twonote.adapter.RecycleAdapter
import com.example.twonote.data.local.local.DBHelper
import com.example.twonote.data.local.local.dao.NotesDao
import com.example.twonote.databinding.ActivityRecycleBinBinding
import com.example.twonote.databinding.ListItemRecycleBinBinding

class RecycleBinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecycleBinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

    }
    private fun initRecycler() {
        val dao = NotesDao(DBHelper(this))
        val adapter = RecycleAdapter(this, dao)
        binding.recyclerRecycleBin.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerRecycleBin.adapter = adapter
    }
}