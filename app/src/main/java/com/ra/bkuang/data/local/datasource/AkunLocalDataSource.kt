package com.ra.bkuang.data.local.datasource

import com.ra.bkuang.data.local.database.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunLocalDataSource {
  fun getTotalMoney(): Flow<Long?>
  suspend fun save(tabungan: AkunEntity)
  suspend fun delete(tabungan: AkunEntity)
  suspend fun update(tabungan: AkunEntity)
  suspend fun findAll(): List<AkunEntity>
  suspend fun findById(id: UUID): AkunEntity
}