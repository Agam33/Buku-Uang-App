package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.alarm.DebtAlarm
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.SetAlarmDebt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class SetAlarmDebtImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val debtAlarm: DebtAlarm,
): SetAlarmDebt {
  override suspend fun invoke(
    calendar: Calendar,
    model: HutangModel
  ): Boolean = withContext(ioDispatcher) {
    debtAlarm.setAlarm(model, calendar)
    return@withContext true
  }
}