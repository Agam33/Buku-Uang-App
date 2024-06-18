package com.ra.bkuang.features.account.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AkunRepositoryImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val localDataSource: AkunLocalDataSource
): AkunRepository {
  override fun getTotalMoney(): Flow<Long?> {
    return localDataSource.getTotalMoney()
  }

  override fun save(akun: AkunModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.save(akun.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message ?: ""))
      }
    }.flowOn(ioDispatcher)
  }

  override fun delete(akun: AkunModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.delete(akun.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message ?: ""))
      }
    }.flowOn(ioDispatcher)
  }

  override fun update(akun: AkunModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.update(akun.toEntity())
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message ?: ""))
      }
    }.flowOn(ioDispatcher)
  }

  override fun findAllWithFlow(): Flow<List<AkunModel>> {
    return localDataSource.findAllWithFlow().map { accounts ->
      accounts.map { it.toModel() }
    }
  }

  override suspend fun findById(id: UUID): AkunModel {
    return localDataSource.findById(id).toModel()
  }

  override fun findAll(): List<AkunModel> {

    return localDataSource.findAll().map { it.toModel() }
  }
}