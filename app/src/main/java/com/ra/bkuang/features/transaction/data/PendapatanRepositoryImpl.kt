package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.local.AkunLocalDataSource
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PendapatanRepositoryImpl @Inject constructor(
  @IoDispatcherQualifier private val  ioDispatcher: CoroutineDispatcher,
  private val localDataSource: PendapatanLocalDataSource,
  private val accountLocalData: AkunLocalDataSource
): PendapatanRepository {
  override suspend fun findById(uuid: UUID): PendapatanModel {
    return localDataSource.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailPendapatan {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTotalPendapatanByDateWithFlow(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long> {
    return localDataSource.getTotalPendapatanByDateWithFlow(fromDate, toDate).map { it ?: 0 }
  }

  override suspend fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return localDataSource.getTotalPendapatan(fromDate, toDate)
  }

  override fun getTotalPendapatanWithFlow(): Flow<Long?> {
    return localDataSource.getTotalPendapatan()
  }

  override fun getListDetailPendapatanByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<Result<List<DetailPendapatan>>> {
    return flow {
      localDataSource.getPendapatanByDate(fromDate, toDate).collect { list ->
        if(list.isEmpty()) {
          emit(Result.Error("Empty list."))
          return@collect
        }
        emit(Result.Success(list))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun save(incomeModel: PendapatanModel): Flow<Result<Boolean>> {
    return flow {
      try {
        val account = accountLocalData.findById(incomeModel.idAkun).toModel()

        account.total += incomeModel.jumlah

        accountLocalData.update(account.toEntity())

        localDataSource.save(incomeModel.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun delete(uuid: UUID): Flow<Result<Boolean>> {
    return flow {
      try {
        val pendapatan = localDataSource.findById(uuid).toModel()

        localDataSource.delete(pendapatan.toEntity())

        val account = accountLocalData.findById(pendapatan.idAkun).toModel()
        account.total -= pendapatan.jumlah

        accountLocalData.update(account.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }

  override fun update(newIncomeModel: PendapatanModel, oldIncomeModel: PendapatanModel): Flow<Result<Boolean>> {
    return flow {
      try {
        localDataSource.update(newIncomeModel.toEntity())

        val newAccount = accountLocalData.findById(newIncomeModel.idAkun).toModel()

        newAccount.total += newIncomeModel.jumlah

        accountLocalData.update(newAccount.toEntity())

        val oldAccount = accountLocalData.findById(oldIncomeModel.idAkun).toModel()

        oldAccount.total -= oldIncomeModel.jumlah

        accountLocalData.update(oldAccount.toEntity())

        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
      .catch { emit(Result.Error(it.message.toString())) }
      .flowOn(ioDispatcher)
  }
}