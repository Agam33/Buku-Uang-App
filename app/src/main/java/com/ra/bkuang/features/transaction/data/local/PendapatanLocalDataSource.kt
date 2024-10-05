package com.ra.bkuang.features.transaction.data.local

import com.ra.bkuang.core.data.source.local.database.entity.DetailPendapatan
import com.ra.bkuang.core.data.source.local.database.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanLocalDataSource {
  suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long?
  suspend fun findById(uuid: UUID): PendapatanEntity
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDateWithFlow(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatan(): Flow<Long?>
  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailPendapatan>>
  suspend fun save(pendapatan: PendapatanEntity)
  suspend fun delete(pendapatan: PendapatanEntity)
  suspend fun update(pendapatan: PendapatanEntity)
}