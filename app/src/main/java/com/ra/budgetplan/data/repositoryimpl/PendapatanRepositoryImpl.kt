package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PendapatanLocalDataSource
import com.ra.budgetplan.domain.mapper.PendapatanMapper
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.repository.PendapatanRepository
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  private val localDataSource: PendapatanLocalDataSource
): PendapatanRepository {
  override suspend fun save(pendapatan: PendapatanModel) {
    return localDataSource.savePendapatan(PendapatanMapper.pendapatanToEntity(pendapatan))
  }

  override suspend fun delete(pendapatan: PendapatanModel) {
    return localDataSource.deletePendapatan(PendapatanMapper.pendapatanToEntity(pendapatan))
  }

  override suspend fun update(pendapatan: PendapatanModel) {
    return localDataSource.updatePendapatan(PendapatanMapper.pendapatanToEntity(pendapatan))
  }
}