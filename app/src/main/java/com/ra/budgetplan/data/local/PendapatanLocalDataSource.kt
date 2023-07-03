package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanLocalDataSource {
  suspend fun findById(uuid: UUID): PendapatanEntity
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatan(): Flow<Long?>
  suspend fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
  suspend fun savePendapatan(pendapatan: PendapatanEntity)
  suspend fun deletePendapatan(pendapatan: PendapatanEntity)
  suspend fun updatePendapatan(pendapatan: PendapatanEntity)
}