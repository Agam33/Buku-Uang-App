package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.PengeluaranEntity
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  private val localDataSource: PengeluaranLocalDataSource
): PengeluaranRepository {
  override suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    idKategori: UUID
  ): Long? {
    return localDataSource.getTotalPengeluaranByDateAndKategory(fromDate, toDate, idKategori)
  }

  override suspend fun getTotalPengeluaran(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return localDataSource.getTotalPengeluaran(fromDate, toDate)
  }

  override suspend fun findById(uuid: UUID): PengeluaranEntity {
    return localDataSource.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailPengeluaran {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTotalPengeluaranByDate(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return localDataSource.getTotalPengeluaranByDate(fromDate, toDate)
  }

  override fun getTotalPengeluaran(): Flow<Long?> {
    return localDataSource.getTotalPengeluaran()
  }

  override fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>> {
    return localDataSource.getMonthlyPengeluaran(startOfDay, endOfDay)
  }

  override suspend fun getPengeluaranByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPengeluaran> {
    return localDataSource.getPengeluaranByDate(fromDate, toDate)
  }

  override suspend fun save(pengeluaran: PengeluaranEntity) {
    return localDataSource.savePengeluaran(pengeluaran)
  }

  override suspend fun delete(pengeluaran: PengeluaranEntity) {
    return localDataSource.deletePengeluaran(pengeluaran)
  }

  override suspend fun update(pengeluaran: PengeluaranEntity) {
    return localDataSource.updatePengeluaran(pengeluaran)
  }
}