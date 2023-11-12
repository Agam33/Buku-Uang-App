package com.ra.bkuang.data.local

import com.ra.bkuang.data.local.entity.DetailPendapatan
import com.ra.bkuang.data.local.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanLocalDataSource {
  suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long?
  suspend fun findById(uuid: UUID): PendapatanEntity
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatan(): Flow<Long?>
  suspend fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
  suspend fun savePendapatan(pendapatan: PendapatanEntity)
  suspend fun deletePendapatan(pendapatan: PendapatanEntity)
  suspend fun updatePendapatan(pendapatan: PendapatanEntity)
}