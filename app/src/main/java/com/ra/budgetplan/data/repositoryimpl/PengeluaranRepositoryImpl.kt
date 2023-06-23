package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PengeluaranLocalDataSource
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  private val localDataSource: PengeluaranLocalDataSource
): PengeluaranRepository {
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