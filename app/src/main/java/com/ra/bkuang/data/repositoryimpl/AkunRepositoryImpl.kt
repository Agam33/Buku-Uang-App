package com.ra.bkuang.data.repositoryimpl

import com.ra.bkuang.data.local.AkunLocalDataSource
import com.ra.bkuang.data.local.entity.AkunEntity
import com.ra.bkuang.domain.repository.AkunRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AkunRepositoryImpl @Inject constructor(
  private val localDataSource: AkunLocalDataSource
): AkunRepository {
  override fun getTotalMoney(): Flow<Long?> {
    return localDataSource.getTotalMoney()
  }

  override suspend fun save(akun: AkunEntity) {
    return localDataSource.save(akun)
  }

  override suspend fun delete(akun: AkunEntity) {
    return localDataSource.delete(akun)
  }

  override suspend fun update(akun: AkunEntity) {
    return localDataSource.update(akun)
  }

  override suspend fun findAll(): List<AkunEntity> {
    return localDataSource.findAll()
  }

  override suspend fun findById(id: UUID): AkunEntity {
    return localDataSource.findById(id)
  }
}