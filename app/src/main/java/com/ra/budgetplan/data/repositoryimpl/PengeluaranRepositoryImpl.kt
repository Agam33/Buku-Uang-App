package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PengeluaranLocalDataSource
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  private val localDataSource: PengeluaranLocalDataSource
): PengeluaranRepository {
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