package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.alarm.DebtAlarmManager
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.CancelAlarmDebt
import javax.inject.Inject

class CancelAlarmDebtImpl @Inject constructor(
  private val alarm: DebtAlarmManager
): CancelAlarmDebt {
  override suspend fun invoke(model: HutangModel) {
    alarm.cancelAlarm(model)
  }
}