package com.ra.bkuang.features.debt.domain

import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HutangRepository {
  suspend fun findByAlarmId(alarmId: Int): HutangModel
  suspend fun save(hutang: HutangModel)
  suspend fun delete(hutang: HutangModel)
  suspend fun update(hutang: HutangModel)
  suspend fun findById(id: UUID): HutangModel
  suspend fun findAll(): List<HutangModel>
  fun findByIdWithFlow(id: UUID): Flow<HutangModel?>
}