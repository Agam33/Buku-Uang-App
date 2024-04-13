package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel

interface FindHutangByAlarmId {
  suspend fun invoke(alarmId: Int): HutangModel
}