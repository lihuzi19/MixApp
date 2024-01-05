package com.lihuzi.takenotes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/**
 *@Author lining
 *@Date 2023/6/21
 *@DESC TODO
 **/
open class BaseCoroutineActivity : AppCompatActivity(), CoroutineScope {

    private val job by lazy { Job() }
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}