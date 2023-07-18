package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PengeluaranLocalDataSource {
  suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    id: UUID
  ): Long?

  suspend fun getTotalPengeluaran(fromDate: LocalDateTime, toDate: LocalDateTime): Long?

  suspend fun findById(uuid: UUID): PengeluaranEntity
  suspend fun findDetailById(uuid: UUID): DetailPengeluaran
  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPengeluaran(): Flow<Long?>
  fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>>
  suspend fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
  suspend fun savePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun deletePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun updatePengeluaran(pengeluaran: PengeluaranEntity)
}