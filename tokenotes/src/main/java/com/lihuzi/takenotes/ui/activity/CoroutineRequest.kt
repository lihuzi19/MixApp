package com.lihuzi.takenotes.ui.activity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *@Author lining
 *@Date 2023/6/21
 *@DESC TODO
 **/
class CoroutineRequest {
}

fun CoroutineScope.launchCoroutine(
    beforeBlock: () -> Unit,
    suspendBlock: () -> Unit,
    afterBlock: () -> Unit
) {
    CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
        beforeBlock.invoke()
        async(Dispatchers.IO) {
            suspendBlock.invoke()
        }
        afterBlock.invoke()
    }
}