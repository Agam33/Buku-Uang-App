package com.ra.bkuang.domain.repository

import com.ra.bkuang.data.local.database.entity.AkunEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunRepository {
  fun getTotalMoney(): Flow<Long?>
  suspend fun save(akun: AkunEntity)
  suspend fun delete(akun: AkunEntity)
  suspend fun update(akun: AkunEntity)
  suspend fun findAll(): List<AkunEntity>
  suspend fun findById(id: UUID): AkunEntity
}