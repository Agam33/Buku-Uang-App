package com.ra.bkuang.features.debt.data.local

import com.ra.bkuang.features.debt.data.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HutangLocalDataSource {
  suspend fun findByAlarmId(alarmId: Int): HutangEntity
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
  suspend fun findById(id: UUID): HutangEntity
  suspend fun findAll(): List<HutangEntity>
  fun findByIdWithFlow(id: UUID): Flow<HutangEntity?>
}