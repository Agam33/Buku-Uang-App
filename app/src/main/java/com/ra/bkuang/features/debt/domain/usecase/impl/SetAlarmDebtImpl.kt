package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class SetAlarmDebtImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val debtAlarmManager: DebtAlarmManager,
): SetAlarmDebt {
  override suspend fun invoke(
    calendar: Calendar,
    model: HutangModel
  ): Boolean = withContext(ioDispatcher) {
    debtAlarmManager.setAlarm(model, calendar)
    return@withContext true
  }
}