package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface PendapatanRepository {
  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatan(): Flow<Long?>
  suspend fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
  suspend fun save(pendapatan: PendapatanEntity)
  suspend fun delete(pendapatan: PendapatanEntity)
  suspend fun update(pendapatan: PendapatanEntity)
}