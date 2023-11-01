package com.ra.bkuang.data.repositoryimpl

import com.ra.bkuang.data.local.PendapatanLocalDataSource
import com.ra.bkuang.data.entity.DetailPendapatan
import com.ra.bkuang.data.entity.PendapatanEntity
import com.ra.bkuang.domain.repository.PendapatanRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  private val localDataSource: PendapatanLocalDataSource
): PendapatanRepository {
  override suspend fun findById(uuid: UUID): PendapatanEntity {
    return localDataSource.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailPendapatan {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTotalPendapatanByDate(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return localDataSource.getTotalPendapatanByDate(fromDate, toDate)
  }

  override suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return localDataSource.getTotalPendapatan(fromDate, toDate)
  }

  override fun getTotalPendapatan(): Flow<Long?> {
    return localDataSource.getTotalPendapatan()
  }

  override suspend fun getPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPendapatan> {
    return localDataSource.getPendapatanByDate(fromDate, toDate)
  }

  override suspend fun save(pendapatan: PendapatanEntity) {
    return localDataSource.savePendapatan(pendapatan)
  }

  override suspend fun delete(pendapatan: PendapatanEntity) {
    return localDataSource.deletePendapatan(pendapatan)
  }

  override suspend fun update(pendapatan: PendapatanEntity) {
    return localDataSource.updatePendapatan(pendapatan)
  }
}