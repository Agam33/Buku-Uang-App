package com.ra.bkuang.features.transaction.alarm

import android.content.Context
import java.util.Calendar

interface TransactionAlarmManager {
  fun setAlarm(calendar: Calendar)
  fun setOnNextDay(hour: Int, minute: Int)
  fun cancelAlarm()
  fun showNotification(ctx: Context)
}