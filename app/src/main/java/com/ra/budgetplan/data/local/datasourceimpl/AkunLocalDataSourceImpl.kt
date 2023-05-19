package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.TabunganLocalDataSource
import com.ra.budgetplan.data.local.database.dao.AkunDao
import com.ra.budgetplan.domain.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AkunLocalDataSourceImpl @Inject constructor(
  private val akunDao: AkunDao
): TabunganLocalDataSource {
  override suspend fun save(tabungan: AkunEntity) {
    return akunDao.save(tabungan)
  }

  override suspend fun delete(tabungan: AkunEntity) {
    return akunDao.delete(tabungan)
  }

  override suspend fun update(tabungan: AkunEntity) {
    return akunDao.update(tabungan)
  }

  override fun findAll(): Flow<List<AkunEntity>> {
    return akunDao.findAll()
  }

  override fun findById(id: UUID): Flow<AkunEntity> {
    return akunDao.findById(id)
  }
}