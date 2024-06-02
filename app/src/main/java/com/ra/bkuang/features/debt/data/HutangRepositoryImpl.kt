package com.ra.bkuang.features.debt.data

import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.data.local.HutangLocalDataSource
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class HutangRepositoryImpl @Inject constructor(
  private val localDataSource: HutangLocalDataSource,
  private val debtAlarmManager: DebtAlarmManager,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  @IoCoroutineScopeQualifier private val ioScope: CoroutineScope,
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

  override suspend fun update(hutang: HutangModel): Boolean {
    return try {
      localDataSource.update(hutang.toEntity())
      true
    } catch (e: Exception) {
      false
    }
  }

  override suspend fun findById(id: UUID): HutangModel {
    return localDataSource.findById(id).toModel()
  }

  override suspend fun findAll(): List<HutangModel> {
    return localDataSource.findAll().map { it.toModel() }
  }

  override fun findByIdWithFlow(id: String): Flow<HutangModel?> {
    return localDataSource.findByIdWithFlow(UUID.fromString(id)).map { it?.toModel() }
  }

  override suspend fun setAlarmDebt(calendar: Calendar, model: HutangModel): Boolean = withContext(ioDispatcher) {
    debtAlarmManager.setAlarm(calendar = calendar, model = model) {
      ioScope.launch {
        localDataSource.update(it.toEntity())
      }
    }
    return@withContext true
  }
}