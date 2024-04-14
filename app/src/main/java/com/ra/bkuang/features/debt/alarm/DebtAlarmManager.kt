package com.ra.bkuang.features.debt.alarm

import android.content.Context
import com.ra.bkuang.features.debt.domain.model.HutangModel
import java.util.Calendar

interface DebtAlarmManager {
  fun setAlarm(model: HutangModel, calendar: Calendar, callback: (HutangModel) -> Unit)
  fun cancelAlarm(model: HutangModel, callback: (HutangModel) -> Unit)
  fun showNotification(
    context: Context,
    debtModelId: String,
    title: String,
  )
}