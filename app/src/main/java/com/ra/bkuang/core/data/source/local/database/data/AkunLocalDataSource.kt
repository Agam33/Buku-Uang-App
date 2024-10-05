package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunLocalDataSource {
  fun getTotalMoney(): Flow<Long?>
  suspend fun save(tabungan: AkunEntity)
  suspend fun delete(tabungan: AkunEntity)
  suspend fun update(tabungan: AkunEntity)
  fun findAllWithFlow(): Flow<List<AkunEntity>>
  suspend fun findById(id: UUID): AkunEntity
  fun findAll(): List<AkunEntity>
}