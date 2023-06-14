package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface PendapatanLocalDataSource {
  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailPendapatan>>
  suspend fun savePendapatan(pendapatan: PendapatanEntity)
  suspend fun deletePendapatan(pendapatan: PendapatanEntity)
  suspend fun updatePendapatan(pendapatan: PendapatanEntity)
}