package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.HutangLocalDataSource
import com.ra.budgetplan.domain.mapper.HutangMapper
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import javax.inject.Inject

class HutangRepositoryImpl @Inject constructor(
  private val localDataSource: HutangLocalDataSource
): HutangRepository {
  override suspend fun save(hutang: HutangModel) {
    localDataSource.save(HutangMapper.hutangToEntity(hutang))
  }

  override suspend fun delete(hutang: HutangModel) {
    localDataSource.delete(HutangMapper.hutangToEntity(hutang))
  }

  override suspend fun update(hutang: HutangModel) {
    localDataSource.update(HutangMapper.hutangToEntity(hutang))
  }
}