package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.dao.AkunDao
import com.ra.bkuang.core.data.source.local.database.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AkunLocalDataSourceImpl @Inject constructor(
  private val akunDao: AkunDao
): AkunLocalDataSource {
  override fun getTotalMoney(): Flow<Long?> {
    return akunDao.getTotalMoney()
  }

  override suspend fun save(tabungan: AkunEntity) {
    return akunDao.save(tabungan)
  }

  override suspend fun delete(tabungan: AkunEntity) {
    return akunDao.delete(tabungan)
  }

  override suspend fun update(tabungan: AkunEntity) {
    return akunDao.update(tabungan)
  }

  override fun findAllWithFlow(): Flow<List<AkunEntity>> {
    return akunDao.findAllWithFlow()
  }

  override suspend fun findById(id: UUID): AkunEntity {
    return akunDao.findById(id)
  }

  override fun findAll(): List<AkunEntity> {
    return akunDao.findAll()
  }
}