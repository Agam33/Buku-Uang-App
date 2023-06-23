package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.PengeluaranLocalDataSource
import com.ra.budgetplan.data.local.database.dao.PengeluaranDao
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class PengeluaranLocalDataSourceImpl @Inject constructor(
  private val pengeluaranDao: PengeluaranDao
): PengeluaranLocalDataSource {
  override fun getTotalPengeluaranByDate(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return pengeluaranDao.getTotalPengeluaranByDate(fromDate, toDate)
  }

  override fun getTotalPengeluaran(): Flow<Long?> {
    return pengeluaranDao.getTotalPengeluaran()
  }

  override fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>> {
    return pengeluaranDao.getMonthlyPengeluaran(startOfDay, endOfDay)
  }

  override suspend fun getPengeluaranByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPengeluaran> {
    return pengeluaranDao.getPengeluaranByDate(fromDate, toDate)
  }

  override suspend fun savePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.save(pengeluaran)
  }

  override suspend fun deletePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.delete(pengeluaran)
  }

  override suspend fun updatePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.update(pengeluaran)
  }
}