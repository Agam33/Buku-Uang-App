package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.data.model.HutangModel

interface CancelAlarmDebtUseCase {
  suspend fun invoke(model: HutangModel)
}