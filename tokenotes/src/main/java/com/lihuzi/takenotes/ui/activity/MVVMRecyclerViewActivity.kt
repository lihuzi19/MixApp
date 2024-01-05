package com.lihuzi.takenotes.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokenotes.R
import com.example.tokenotes.databinding.ActMvvmRecyclerviewBinding
import com.lihuzi.takenotes.ui.viewholder.MVVMViewModel

/**
 *@Author lining
 *@Date 2023/6/30
 *@DESC TODO
 **/
class MVVMRecyclerViewActivity : AppCompatActivity() {
    private val binding: ActMvvmRecyclerviewBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.act_mvvm_recyclerview
        )
    }

    private val viewModel: MVVMViewModel by lazy {
        ViewModelProvider(this)[MVVMViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.rv.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "模拟一下loading", Toast.LENGTH_SHORT).show()
        binding.rv.postDelayed({ viewModel.getDataFromUnKnow() }, 1000)
    }

}