package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.CicilanModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CicilanRepository {
  suspend fun save(cicilan: CicilanModel)
  suspend fun delete(cicilan: CicilanModel)
  suspend fun update(cicilan: CicilanModel)
  fun findById(id: UUID): Flow<CicilanModel>
  fun findAll(): Flow<List<CicilanModel>>
}