package com.ra.bkuang.features.account.domain

import com.ra.bkuang.features.account.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunRepository {
  fun getTotalMoney(): Flow<Long?>
  suspend fun save(akun: AkunModel)
  suspend fun delete(akun: AkunModel)
  suspend fun update(akun: AkunModel)
  suspend fun findAll(): List<AkunModel>
  suspend fun findById(id: UUID): AkunModel
}