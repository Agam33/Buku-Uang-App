package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.PendapatanLocalDataSource
import com.ra.budgetplan.data.local.database.dao.PendapatanDao
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class PendapatanLocalDataSourceImpl @Inject constructor(
  private val pendapatanDao: PendapatanDao
): PendapatanLocalDataSource {
  override fun getTotalPendapatan(): Flow<Long?> {
    return pendapatanDao.getTotalPendapatan()
  }

  override fun getPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailPendapatan>> {
    return pendapatanDao.getPendapatanByDate(fromDate, toDate)
  }

  override suspend fun savePendapatan(pendapatan: PendapatanEntity) {
    return pendapatanDao.save(pendapatan)
  }

  override suspend fun deletePendapatan(pendapatan: PendapatanEntity) {
    return pendapatanDao.delete(pendapatan)
  }

  override suspend fun updatePendapatan(pendapatan: PendapatanEntity) {
    return pendapatanDao.update(pendapatan)
  }
}