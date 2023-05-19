package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TabunganLocalDataSource {
  suspend fun save(tabungan: AkunEntity)
  suspend fun delete(tabungan: AkunEntity)
  suspend fun update(tabungan: AkunEntity)
  fun findAll(): Flow<List<AkunEntity>>
  fun findById(id: UUID): Flow<AkunEntity>
}