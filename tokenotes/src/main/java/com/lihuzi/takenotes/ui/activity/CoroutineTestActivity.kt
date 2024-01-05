package com.lihuzi.takenotes.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tokenotes.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 *@Author lining
 *@Date 2023/6/21
 *@DESC TODO
 **/
class CoroutineTestActivity : BaseCoroutineActivity() {

    private val TAG = "CoroutineTestActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        testCoroutine()
    }

    private fun testCoroutine() {
        for (i in 0 until 10) {
            launch {
                Log.e(TAG, "$i launch $i")
                var result = async(Dispatchers.IO) {
                    var sleepTime = Random.nextLong(100000)
                    Log.e(TAG, "$i sleepTime $sleepTime")
                    Thread.sleep(sleepTime)
                    sleepTime
                }
                result.invokeOnCompletion {
                    if (result.isCancelled) {
                        Log.e(TAG, "$i isCancelled ${result.isCancelled}")
                    }
                    Log.e(TAG, "$i invokeOnCompletion")
                }
                var time = result.await()
                Log.e(TAG, "$i result.await $time")
            }
        }
    }


}