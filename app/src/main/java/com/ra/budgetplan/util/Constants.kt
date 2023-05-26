package com.ra.budgetplan.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val DATE_PATTERN = "yyyy-MM-dd"
const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"

fun coroutineIOThread(action: suspend () -> Unit) {
  CoroutineScope(Dispatchers.IO).launch {
    action()
  }
}
