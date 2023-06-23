package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface PengeluaranLocalDataSource {
  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPengeluaran(): Flow<Long?>
  fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>>
  suspend fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
  suspend fun savePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun deletePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun updatePengeluaran(pengeluaran: PengeluaranEntity)
}