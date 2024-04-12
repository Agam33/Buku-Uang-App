package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel

interface FindHutangByAlarmId {
  suspend fun invoke(alarmId: Int): HutangModel
}