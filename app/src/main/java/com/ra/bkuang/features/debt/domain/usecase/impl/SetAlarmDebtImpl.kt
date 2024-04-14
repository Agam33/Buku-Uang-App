package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebt
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class SetAlarmDebtImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  @IoCoroutineScopeQualifier private val ioScope: CoroutineScope,
  private val updateHutang: UpdateHutang,
  private val debtAlarmManager: DebtAlarmManager,
): SetAlarmDebt {
  override suspend fun invoke(
    calendar: Calendar,
    model: HutangModel
  ): Boolean = withContext(ioDispatcher) {
    debtAlarmManager.setAlarm(model, calendar) {
      ioScope.launch {
        updateHutang.invoke(it)
      }
    }
    return@withContext true
  }
}