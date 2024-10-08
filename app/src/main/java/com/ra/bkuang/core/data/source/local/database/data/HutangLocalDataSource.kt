package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HutangLocalDataSource {
  suspend fun findByAlarmId(alarmId: Int): HutangEntity
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
  suspend fun findById(id: UUID): HutangEntity
  fun findAllWithFlow(): Flow<List<HutangEntity>>
  fun findByIdWithFlow(id: UUID): Flow<HutangEntity?>
}