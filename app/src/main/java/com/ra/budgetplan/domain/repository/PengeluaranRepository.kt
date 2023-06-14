package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.Date

interface PengeluaranRepository {
  fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>>
  fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailPengeluaran>>
  suspend fun save(pengeluaran: PengeluaranEntity)
  suspend fun delete(pengeluaran: PengeluaranEntity)
  suspend fun update(pengeluaran: PengeluaranEntity)
}