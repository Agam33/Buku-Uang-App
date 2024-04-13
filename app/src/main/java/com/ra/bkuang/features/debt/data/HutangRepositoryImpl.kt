package com.ra.bkuang.features.debt.data

import com.ra.bkuang.features.debt.data.local.HutangLocalDataSource
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class HutangRepositoryImpl @Inject constructor(
  private val localDataSource: HutangLocalDataSource
): HutangRepository {
  override suspend fun findByAlarmId(alarmId: Int): HutangModel {
    return localDataSource.findByAlarmId(alarmId).toModel()
  }

  override suspend fun save(hutang: HutangModel) {
    return localDataSource.save(hutang.toEntity())
  }

  override suspend fun delete(hutang: HutangModel) {
    return localDataSource.delete(hutang.toEntity())
  }

  override suspend fun update(hutang: HutangModel) {
    return localDataSource.update(hutang.toEntity())
  }

  override suspend fun findById(id: UUID): HutangModel {
    return localDataSource.findById(id).toModel()
  }

  override suspend fun findAll(): List<HutangModel> {
    return localDataSource.findAll().map { it.toModel() }
  }

  override fun findByIdWithFlow(id: UUID): Flow<HutangModel?> {
    return localDataSource.findByIdWithFlow(id).map { it?.toModel() }
  }
}