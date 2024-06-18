package com.ra.bkuang.features.debt.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.data.local.HutangLocalDataSource
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

  override fun save(hutang: HutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.save(hutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun delete(hutang: HutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
       localDataSource.delete(hutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override fun update(hutang: HutangModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.update(hutang.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }.flowOn(ioDispatcher)
  }

  override suspend fun findById(id: UUID): HutangModel {
    return localDataSource.findById(id).toModel()
  }

  override fun findAllWithFlow(): Flow<Result<List<HutangModel>>> {
    return flow {
      localDataSource.findAllWithFlow().collect { data ->
        if(data.isEmpty()) {
          emit(Result.Error("Empty list"))
          return@collect
        }

        emit(Result.Success(data.map { it.toModel() }))
      }
    }
  }

  override fun findByIdWithFlow(id: String): Flow<Result<HutangModel?>> {
    return flow {
      localDataSource
        .findByIdWithFlow(UUID.fromString(id))
        .map {
          it?.toModel()
        }.collect {
          emit(Result.Success(it))
      }
    }.flowOn(ioDispatcher)
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