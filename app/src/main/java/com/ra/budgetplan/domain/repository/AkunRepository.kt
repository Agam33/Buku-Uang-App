package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunRepository {
  suspend fun save(akunModel: AkunModel)
  suspend fun delete(akunModel: AkunModel)
  suspend fun update(akunModel: AkunModel)
  fun findAll(): Flow<List<AkunModel>>
  fun findById(id: UUID): Flow<AkunModel>
}