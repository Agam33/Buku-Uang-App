package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HutangRepository {
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
  suspend fun findById(id: UUID): HutangEntity
  suspend fun findAll(): List<HutangEntity>
  fun findByIdWithFlow(id: UUID): Flow<HutangEntity>
}