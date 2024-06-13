package com.ra.bkuang.features.transaction.data.local

import com.ra.bkuang.core.database.dao.PendapatanDao
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PendapatanLocalDataSourceImpl @Inject constructor(
  private val pendapatanDao: PendapatanDao
): PendapatanLocalDataSource {
  override suspend fun findById(uuid: UUID): PendapatanEntity {
    return pendapatanDao.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailPendapatan {
    return pendapatanDao.findDetailPendapatanById(uuid)
  }

  override fun getTotalPendapatanByDateWithFlow(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return pendapatanDao.getTotalPendapatanByDateWithFlow(fromDate, toDate)
  }

  override suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return pendapatanDao.getTotalPendapatan(fromDate, toDate)
  }

  override fun getTotalPendapatan(): Flow<Long?> {
    return pendapatanDao.getTotalPendapatan()
  }

  override fun getPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailPendapatan>> {
    return pendapatanDao.getPendapatanByDate(fromDate, toDate)
  }

  override suspend fun save(pendapatan: PendapatanEntity) {
    return pendapatanDao.save(pendapatan)
  }

  override suspend fun delete(pendapatan: PendapatanEntity) {
    return pendapatanDao.delete(pendapatan)
  }

  override suspend fun update(pendapatan: PendapatanEntity) {
    return pendapatanDao.update(pendapatan)
  }
}