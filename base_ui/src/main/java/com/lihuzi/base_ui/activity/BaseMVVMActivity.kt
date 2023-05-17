package com.lihuzi.base_ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseMVVMActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[viewModel::class.java]
        setContentView(getLayoutId())
        initView()
    }

    open fun initView() {
    }

    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}