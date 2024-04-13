package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebt
import javax.inject.Inject

class CancelAlarmDebtImpl @Inject constructor(
  private val alarm: DebtAlarmManager
): CancelAlarmDebt {
  override suspend fun invoke(model: HutangModel) {
    alarm.cancelAlarm(model)
  }
}