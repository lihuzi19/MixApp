package com.lihuzi.takenotes.ui.activity

import com.example.tokenotes.R
import com.example.tokenotes.databinding.ActivityMainTakenotesBinding
import com.lihuzi.base_ui.activity.BaseMVVMActivity
import com.lihuzi.base_ui.viewmodel.BaseViewModel

class NotesMainActivity : BaseMVVMActivity<ActivityMainTakenotesBinding, BaseViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main_takenotes
    }

    override fun initView(){

    }

}