package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.CicilanLocalDataSource
import com.ra.budgetplan.domain.entity.CicilanEntity
import com.ra.budgetplan.domain.repository.CicilanRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CicilanRepositoryImpl @Inject constructor(
  private val localDataSource: CicilanLocalDataSource
): CicilanRepository {
  override suspend fun save(cicilan: CicilanEntity) {
    return localDataSource.save(cicilan)
  }

  override suspend fun delete(cicilan: CicilanEntity) {
    return localDataSource.delete(cicilan)
  }

  override suspend fun update(cicilan: CicilanEntity) {
    return localDataSource.update(cicilan)
  }

  override fun findById(id: UUID): Flow<CicilanEntity> {
    return localDataSource.findById(id)
  }

  override fun findAll(): Flow<List<CicilanEntity>> {
    return localDataSource.findAll()
  }
}