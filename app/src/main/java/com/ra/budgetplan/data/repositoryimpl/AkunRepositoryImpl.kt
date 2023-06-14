package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.AkunLocalDataSource
import com.ra.budgetplan.domain.entity.AkunEntity
import com.ra.budgetplan.domain.repository.AkunRepository
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

  override fun findAll(): Flow<List<AkunEntity>> {
    return localDataSource.findAll()
  }

  override fun findById(id: UUID): Flow<AkunEntity> {
    return localDataSource.findById(id)
  }
}