package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CancelAlarmDebtImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val alarm: DebtAlarmManager
): CancelAlarmDebt {
  override suspend fun invoke(model: HutangModel) = withContext(ioDispatcher) {
    return@withContext alarm.cancelAlarm(model)
  }
}