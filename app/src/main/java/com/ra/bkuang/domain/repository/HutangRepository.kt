package com.ra.bkuang.domain.repository

import android.app.PendingIntent
import com.ra.bkuang.data.local.database.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.UUID

interface HutangRepository {
  suspend fun findByAlarmId(alarmId: Int): HutangEntity
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
  suspend fun findById(id: UUID): HutangEntity
  suspend fun findAll(): List<HutangEntity>
  fun findByIdWithFlow(id: UUID): Flow<HutangEntity?>
}