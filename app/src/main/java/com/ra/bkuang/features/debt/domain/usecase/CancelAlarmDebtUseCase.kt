package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel

interface CancelAlarmDebtUseCase {
  suspend fun invoke(model: HutangModel)
}