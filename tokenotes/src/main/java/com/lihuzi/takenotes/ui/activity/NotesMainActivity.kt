package com.lihuzi.takenotes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tokenotes.R
import com.example.tokenotes.databinding.ActivityMainTakenotesBinding
import com.lihuzi.takenotes.viewmodel.NoteMainVM

class NotesMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainTakenotesBinding
    lateinit var viewModel: NoteMainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_takenotes)
        initMVVM()
        initView()
    }

    fun initMVVM() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_takenotes)
        viewModel = ViewModelProvider(this)[NoteMainVM::class.java]
        binding.notesVM = viewModel
        viewModel.initBinding(binding,this)
    }

    fun initView() {
        window.statusBarColor =getColor(R.color.colorPrimary)
        binding.toolbar.title = "记账本"
        binding.toolbar.setTitleTextColor(getColor(android.R.color.white))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        binding.notesVM = null
    }
}