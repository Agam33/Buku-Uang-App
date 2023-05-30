package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.CicilanEntity
import com.ra.budgetplan.domain.model.CicilanModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CicilanRepository {
  suspend fun save(cicilan: CicilanEntity)
  suspend fun delete(cicilan: CicilanEntity)
  suspend fun update(cicilan: CicilanEntity)
  fun findById(id: UUID): Flow<CicilanEntity>
  fun findAll(): Flow<List<CicilanEntity>>
}