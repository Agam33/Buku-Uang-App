package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PendapatanLocalDataSource
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.PendapatanEntity
import com.ra.budgetplan.domain.repository.PendapatanRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  private val localDataSource: PendapatanLocalDataSource
): PendapatanRepository {
  override fun getPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailPendapatan>> {
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