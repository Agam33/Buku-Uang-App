package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.data.model.HutangModel

interface FindHutangByAlarmIdUseCase {
  suspend fun invoke(alarmId: Int): HutangModel
}