package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel
import java.util.Calendar

interface SetAlarmDebt {
  suspend fun invoke(calendar: Calendar, model: HutangModel): Boolean
}