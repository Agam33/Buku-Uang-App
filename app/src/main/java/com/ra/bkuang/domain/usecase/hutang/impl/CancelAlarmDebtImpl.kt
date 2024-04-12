package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.alarm.DebtAlarm
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.CancelAlarmDebt
import javax.inject.Inject

class CancelAlarmDebtImpl @Inject constructor(
  private val alarm: DebtAlarm
): CancelAlarmDebt {
  override suspend fun invoke(model: HutangModel) {
    alarm.cancelAlarm(model)
  }
}