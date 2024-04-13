package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel

interface CancelAlarmDebt {
  suspend fun invoke(model: HutangModel)
}