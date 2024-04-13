package com.ra.bkuang.features.transaction.domain

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PengeluaranRepository {
  suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    idKategori: UUID
  ): Long?

  suspend fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long?

  suspend fun findById(uuid: UUID): PengeluaranModel
  suspend fun findDetailById(uuid: UUID): DetailPengeluaran
  fun getTotalPengeluaranByDateWithFlow(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPengeluaranWithFlow(): Flow<Long?>
  fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>>
  suspend fun getListDetailPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
  suspend fun save(pengeluaran: PengeluaranModel)
  suspend fun delete(pengeluaran: PengeluaranModel)
  suspend fun update(pengeluaran: PengeluaranModel)
}