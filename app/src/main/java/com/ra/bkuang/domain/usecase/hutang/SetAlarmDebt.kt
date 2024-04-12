package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import java.util.Calendar

interface SetAlarmDebt {
  suspend fun invoke(calendar: Calendar, model: HutangModel): Boolean
}