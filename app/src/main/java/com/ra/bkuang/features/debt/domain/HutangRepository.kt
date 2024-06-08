package com.ra.bkuang.features.debt.domain

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.UUID

interface HutangRepository {
  suspend fun findByAlarmId(alarmId: Int): HutangModel
  fun save(hutang: HutangModel): Flow<Result<Boolean>>
  fun delete(hutang: HutangModel): Flow<Result<Boolean>>
  fun update(hutang: HutangModel): Flow<Result<Boolean>>
  suspend fun findById(id: UUID): HutangModel
  fun findAllWithFlow(): Flow<Result<List<HutangModel>>>
  fun findByIdWithFlow(id: String): Flow<Result<HutangModel?>>
  suspend fun setAlarmDebt(calendar: Calendar, model: HutangModel): Boolean
}