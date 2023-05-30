package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PendapatanLocalDataSource
import com.ra.budgetplan.domain.entity.PendapatanEntity
import com.ra.budgetplan.domain.repository.PendapatanRepository
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  private val localDataSource: PendapatanLocalDataSource
): PendapatanRepository {
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