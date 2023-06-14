package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.HutangLocalDataSource
import com.ra.budgetplan.domain.entity.HutangEntity
import com.ra.budgetplan.domain.repository.HutangRepository
import javax.inject.Inject

class HutangRepositoryImpl @Inject constructor(
  private val localDataSource: HutangLocalDataSource
): HutangRepository {
  override suspend fun save(hutang: HutangEntity) {
    localDataSource.save(hutang)
  }

  override suspend fun delete(hutang: HutangEntity) {
    localDataSource.delete(hutang)
  }

  override suspend fun update(hutang: HutangEntity) {
    localDataSource.update(hutang)
  }
}