package com.lihuzi.takenotes.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tokenotes.R
import com.example.tokenotes.databinding.ActivityMainTakenotesBinding

class NotesMainActivity : AppCompatActivity() {

    var binding: ActivityMainTakenotesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main_takenotes)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_takenotes)
    }
}