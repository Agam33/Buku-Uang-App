package com.ra.bkuang.util

import androidx.activity.OnBackPressedCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Utils {
    inline fun coroutineIOThread(crossinline action: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            action()
        }
    }

    fun calculatePercent(currValue: Int, maxValue: Long): Double =
        ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0

}