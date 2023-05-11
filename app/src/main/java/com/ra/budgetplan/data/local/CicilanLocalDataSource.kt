package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.CicilanEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CicilanLocalDataSource {
  suspend fun save(cicilan: CicilanEntity)
  suspend fun delete(cicilan: CicilanEntity)
  suspend fun update(cicilan: CicilanEntity)
  fun findById(id: UUID): Flow<CicilanEntity>
  fun findAll(): Flow<List<CicilanEntity>>
}