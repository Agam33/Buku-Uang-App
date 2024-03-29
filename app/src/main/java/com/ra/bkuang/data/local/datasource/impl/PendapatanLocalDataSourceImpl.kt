package com.ra.bkuang.data.local.datasource.impl

import com.ra.bkuang.data.local.datasource.PendapatanLocalDataSource
import com.ra.bkuang.data.local.database.dao.PendapatanDao
import com.ra.bkuang.data.local.database.entity.DetailPendapatan
import com.ra.bkuang.data.local.database.entity.PendapatanEntity
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

  override fun getTotalPendapatanByDate(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return pendapatanDao.getTotalPendapatanByDate(fromDate, toDate)
  }

  override suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return pendapatanDao.getTotalPendapatan(fromDate, toDate)
  }

  override fun getTotalPendapatan(): Flow<Long?> {
    return pendapatanDao.getTotalPendapatan()
  }

  override suspend fun getPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPendapatan> {
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