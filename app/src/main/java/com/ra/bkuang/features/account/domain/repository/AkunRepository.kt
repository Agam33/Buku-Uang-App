package com.ra.bkuang.features.account.domain.repository

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AkunRepository {
  fun getTotalMoney(): Flow<Long?>
  fun save(akun: AkunModel): Flow<Result<Boolean>>
  fun delete(akun: AkunModel): Flow<Result<Boolean>>
  fun update(akun: AkunModel): Flow<Result<Boolean>>
  fun findAllWithFlow(): Flow<List<AkunModel>>
  suspend fun findById(id: UUID): AkunModel
  fun findAll(): List<AkunModel>
}