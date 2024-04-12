package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel

interface CancelAlarmDebt {
  suspend fun invoke(model: HutangModel)
}