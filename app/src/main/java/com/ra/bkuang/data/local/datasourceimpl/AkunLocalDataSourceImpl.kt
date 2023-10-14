package com.ra.bkuang.data.local.datasourceimpl

import com.ra.bkuang.data.local.AkunLocalDataSource
import com.ra.bkuang.data.local.database.dao.AkunDao
import com.ra.bkuang.domain.entity.AkunEntity
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

  override suspend fun findAll(): List<AkunEntity> {
    return akunDao.findAll()
  }

  override suspend fun findById(id: UUID): AkunEntity {
    return akunDao.findById(id)
  }
}