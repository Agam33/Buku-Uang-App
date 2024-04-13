package com.ra.bkuang.features.account.data

import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.account.data.entity.AkunEntity
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AkunRepositoryImpl @Inject constructor(
  private val localDataSource: AkunLocalDataSource
): AkunRepository {
  override fun getTotalMoney(): Flow<Long?> {
    return localDataSource.getTotalMoney()
  }

  override suspend fun save(akun: AkunModel) {
    return localDataSource.save(akun.toEntity())
  }

  override suspend fun delete(akun: AkunModel) {
    return localDataSource.delete(akun.toEntity())
  }

  override suspend fun update(akun: AkunModel) {
    return localDataSource.update(akun.toEntity())
  }

  override suspend fun findAll(): List<AkunModel> {
    return localDataSource.findAll().map { it.toModel() }
  }

  override suspend fun findById(id: UUID): AkunModel {
    return localDataSource.findById(id).toModel()
  }
}