package com.ra.budgetplan.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun coroutineIOThread(action: suspend () -> Unit) {
  CoroutineScope(Dispatchers.IO).launch {
    action()
  }
}
