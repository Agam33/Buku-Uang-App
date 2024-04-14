package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  private val localDataSource: PendapatanLocalDataSource
): PendapatanRepository {
  override suspend fun findById(uuid: UUID): PendapatanModel {
    return localDataSource.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailPendapatan {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTotalPendapatanByDateWithFlow(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return localDataSource.getTotalPendapatanByDate(fromDate, toDate)
  }

  override suspend fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return localDataSource.getTotalPendapatan(fromDate, toDate)
  }

  override fun getTotalPendapatanWithFlow(): Flow<Long?> {
    return localDataSource.getTotalPendapatan()
  }

  override suspend fun getListDetailPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPendapatan> {
    return localDataSource.getPendapatanByDate(fromDate, toDate)
  }

  override suspend fun save(pendapatan: PendapatanModel) {
    return localDataSource.savePendapatan(pendapatan.toEntity())
  }

  override suspend fun delete(pendapatan: PendapatanModel) {
    return localDataSource.deletePendapatan(pendapatan.toEntity())
  }

  override suspend fun update(pendapatan: PendapatanModel) {
    return localDataSource.updatePendapatan(pendapatan.toEntity())
  }
}