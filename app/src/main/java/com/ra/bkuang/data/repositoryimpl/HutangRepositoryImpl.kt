package com.ra.bkuang.data.repositoryimpl

import com.ra.bkuang.data.local.HutangLocalDataSource
import com.ra.bkuang.data.entity.HutangEntity
import com.ra.bkuang.domain.repository.HutangRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class HutangRepositoryImpl @Inject constructor(
  private val localDataSource: HutangLocalDataSource
): HutangRepository {
  override suspend fun save(hutang: HutangEntity) {
    return localDataSource.save(hutang)
  }

  override suspend fun delete(hutang: HutangEntity) {
    return localDataSource.delete(hutang)
  }

  override suspend fun update(hutang: HutangEntity) {
    return localDataSource.update(hutang)
  }

  override suspend fun findById(id: UUID): HutangEntity {
    return localDataSource.findById(id)
  }

  override suspend fun findAll(): List<HutangEntity> {
    return localDataSource.findAll()
  }

  override fun findByIdWithFlow(id: UUID): Flow<HutangEntity> {
    return localDataSource.findByIdWithFlow(id)
  }
}