package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.CicilanLocalDataSource
import com.ra.budgetplan.data.local.database.dao.CicilanDao
import com.ra.budgetplan.domain.entity.CicilanEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class CicilanLocalDataSourceImpl @Inject constructor(
  private val cicilanDao: CicilanDao
): CicilanLocalDataSource {
  override suspend fun save(cicilan: CicilanEntity) {
    return cicilanDao.save(cicilan)
  }

  override suspend fun delete(cicilan: CicilanEntity) {
    return cicilanDao.delete(cicilan)
  }

  override suspend fun update(cicilan: CicilanEntity) {
    return cicilanDao.update(cicilan)
  }

  override fun findById(id: UUID): Flow<CicilanEntity> {
    return cicilanDao.findById(id)
  }

  override fun findAll(): Flow<List<CicilanEntity>> {
    return cicilanDao.findAll()
  }
}