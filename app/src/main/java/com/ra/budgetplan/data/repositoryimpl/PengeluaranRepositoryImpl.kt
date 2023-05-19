package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PengeluaranLocalDataSource
import com.ra.budgetplan.domain.mapper.PengeluaranMapper
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  private val localDataSource: PengeluaranLocalDataSource
): PengeluaranRepository {
  override suspend fun save(pengeluaran: PengeluaranModel) {
    return localDataSource.savePengeluaran(PengeluaranMapper.pengeluaranToEntity(pengeluaran))
  }

  override suspend fun delete(pengeluaran: PengeluaranModel) {
    return localDataSource.deletePengeluaran(PengeluaranMapper.pengeluaranToEntity(pengeluaran))
  }

  override suspend fun update(pengeluaran: PengeluaranModel) {
    return localDataSource.updatePengeluaran(PengeluaranMapper.pengeluaranToEntity(pengeluaran))
  }
}