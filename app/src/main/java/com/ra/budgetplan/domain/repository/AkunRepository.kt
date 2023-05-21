package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.AkunEntity
import com.ra.budgetplan.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunRepository {
  suspend fun save(akun: AkunEntity)
  suspend fun delete(akun: AkunEntity)
  suspend fun update(akun: AkunEntity)
  fun findAll(): Flow<List<AkunEntity>>
  fun findById(id: UUID): Flow<AkunEntity>
}