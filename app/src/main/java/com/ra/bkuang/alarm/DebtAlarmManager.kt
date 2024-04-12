package com.ra.bkuang.alarm

import android.content.Context
import com.ra.bkuang.domain.model.HutangModel
import java.util.Calendar

interface DebtAlarmManager {
  fun setAlarm(model: HutangModel, calendar: Calendar)
  fun cancelAlarm(model: HutangModel)
  fun showNotification(
    context: Context,
    debtModelId: String,
    title: String
  )
}